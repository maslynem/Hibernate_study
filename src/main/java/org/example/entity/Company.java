package org.example.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<User> users;
//    private List<User> users = new ArrayList<>();


    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
