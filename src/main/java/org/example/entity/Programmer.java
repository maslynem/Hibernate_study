package org.example.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Programmer extends User {

    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Integer id, String nickname, String name, LocalDate birthdayDate, Company company, Profile profile, Language language) {
        super(id, nickname, name, birthdayDate, company, profile);
        this.language = language;
    }
}
