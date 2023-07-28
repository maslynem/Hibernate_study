package org.example;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.util.HibernateUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.LockModeType;
import java.util.List;

public class HibernateRunner {

    public static void main(String[] args) {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        TestDataImporter.importData(sessionFactory);
//        session.beginTransaction();
//
//
//        Payment payment = Payment.builder()
//                .amount(100)
//                .receiver(session.get(User.class, 1))
//                .build();
//
//        session.save(payment);
//
//        session.getTransaction().commit();
    }
}