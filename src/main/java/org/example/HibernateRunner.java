package org.example;

import lombok.Cleanup;
import org.example.entity.Company;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {

    public static void main(String[] args) {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();
        Company company = session.get(Company.class, 1);
        session.getTransaction().commit();
    }
}