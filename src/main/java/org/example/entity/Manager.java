package org.example.entity;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Integer id, String nickname, String name, LocalDate birthdayDate, Company company, Profile profile, String projectName) {
        super(id, nickname, name, birthdayDate, company, profile);
        this.projectName = projectName;
    }

}
