package org.example.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Integer id, String nickname, String name, LocalDate birthdayDate, Company company, Profile profile, String projectName) {
        super(id, nickname, name, birthdayDate, company, profile);
        this.projectName = projectName;
    }

}
