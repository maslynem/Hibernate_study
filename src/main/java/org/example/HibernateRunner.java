package org.example;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.QPayment;
import org.example.entity.QProfile;
import org.example.entity.QUser;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateRunner {

    public static void main(String[] args) {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();


//        session.enableFetchProfile("withCompany");
//        session.enableFetchProfile("withPayments");


        session.get(User.class, 1);

//        List<User> users = new JPAQuery<User>(session)
//                .select(QUser.user)
//                .from(QUser.user)
//                .where(QUser.user.firstName.ne("Ivan"))
//                .fetch();
////        users.forEach(user -> user.getPayments().size());
//        users.forEach(user -> user.getCompany().getName());

//        List<User> fetch = new JPAQuery<User>(session)
//                .select(QUser.user)
//                .from(QUser.user)
//                .join(QUser.user.payments).fetchJoin()
//                .join(QUser.user.company).fetchJoin()
//                .fetch();

        session.getTransaction().commit();
    }
}