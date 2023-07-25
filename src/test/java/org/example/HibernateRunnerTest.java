package org.example;

import lombok.Cleanup;
import org.example.entity.Company;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class HibernateRunnerTest {

    @Test
    void createEmptyCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = Company.builder().name("Google").build();
        session.save(company);
        session.getTransaction().commit();
        Assertions.assertTrue(true);
    }

    @Test
    void addTwoUsersToGoogle() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 1);
        User ivan = User.builder().nickname("ivan@mail.ru").name("ivan").birthdayDate(LocalDate.of(1998, 6, 30)).company(company).build();
        User anton = User.builder().nickname("anton@mail.ru").name("anton").birthdayDate(LocalDate.of(1945, 12, 31)).company(company).build();

        session.save(ivan);
        session.save(anton);

        session.getTransaction().commit();
        Assertions.assertTrue(true);

    }

    @Test
    void getCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = session.get(Company.class, 1);
        session.getTransaction().commit();
        Assertions.assertTrue(true);
    }


}