package org.example;

import lombok.Cleanup;
import org.example.entity.Company;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

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
        Assertions.assertEquals("Google", company.getName());
    }

    @Test
    void getUser() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, 1);
        session.getTransaction().commit();
        Assertions.assertEquals("ivan", user.getName());
    }

    @Test
    void saveCompanyWithTwoUsers() {
        User sveta = User.builder().nickname("sveta@mail.ru").name("sveta").birthdayDate(LocalDate.of(2003,5,3)).build();
        User dasha = User.builder().nickname("dasha@mail.ru").name("dasha").birthdayDate(LocalDate.of(2002,12,13)).build();
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = Company.builder().name("Amazon").build();
        company.addUser(sveta);
        company.addUser(dasha);
        session.save(company);
        session.getTransaction().commit();
        Assertions.assertTrue(true);
    }

    @Test
    void removeCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = session.get(Company.class, 2);
        session.delete(company);
        session.getTransaction().commit();
        Assertions.assertTrue(true);
    }

    @Test
    void lazyInitializationException() {
        Company company;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
             company = session.get(Company.class, 1);
            session.getTransaction().commit();
        }
        Set<User> users = company.getUsers();
        Assertions.assertThrows(LazyInitializationException.class, users::size);
    }

}