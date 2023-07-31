package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserCreateDto {
    @NotNull
    private String nickName;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Integer companyID;
}
