package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "company")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nickname;

    @Column
    private String name;

    @Column
    private LocalDate birthdayDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
