package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Company;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class UserReadDto {
    public Integer id;
    public String firstName;
    public String secondName;
    public String nickname;;
    public LocalDate birthDay;
    public CompanyReadDto company;
}
