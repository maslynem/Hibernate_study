package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.UserCreateDto;
import org.example.dto.UserReadDto;
import org.example.entity.User;
import org.example.mapper.Mapper;
import org.example.mapper.UserCreateMapper;
import org.example.mapper.UserReadMapper;
import org.hibernate.graph.GraphSemantic;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Integer create(UserCreateDto userDto) {
        // validation
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreateDto>> validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        User user = userCreateMapper.mapFrom(userDto);
        return userRepository.save(user).getId();
    }

    @Transactional
    public <T> Optional<T> findById(Integer id, Mapper<User, T> mapper) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("WithCompany"));
        return userRepository.findById(id, properties).map(mapper::mapFrom);
    }

    @Transactional
    public Optional<UserReadDto> findById(Integer id) {
       return findById(id, userReadMapper);
    }

    @Transactional
    public boolean delete(Integer id) {
        Optional<User> maybeUser =  userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }

}
