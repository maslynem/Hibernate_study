package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserReadDto;
import org.example.entity.User;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto>{

    private final CompanyReadMapper companyReadMapper;
    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getNickname(),
                object.getBirthdayDate(),
                Optional.ofNullable(object.getCompany()).map(companyReadMapper::mapFrom).orElse(null));
    }
}
