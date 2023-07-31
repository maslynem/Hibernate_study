package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dao.CompanyRepository;
import org.example.dto.UserCreateDto;
import org.example.entity.User;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;
    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .nickname(object.getNickName())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthdayDate(object.getBirthday())
                .company(companyRepository.findById(object.getCompanyID()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
