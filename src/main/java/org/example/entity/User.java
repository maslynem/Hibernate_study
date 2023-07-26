package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.StringUtils.SPACE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nickname;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDate birthdayDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    public String fullName() {
        return firstName + SPACE + lastName;
    }
}
