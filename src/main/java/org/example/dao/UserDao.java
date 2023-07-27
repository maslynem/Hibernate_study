package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.*;
import org.hibernate.Session;

import com.querydsl.core.types.Predicate;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {
    public static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    //    Возвращает всех сотрудников
    public List<User> findAll(Session session) {
//        return session.createQuery("SELECT u FROM User u", User.class).list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//        Root<User> user = criteria.from(User.class);
//        criteria.select(user);
//        return session.createQuery(criteria).list();


        return new JPAQuery<User>(session).select(QUser.user).from(QUser.user).fetch();
    }


    //    Возвращает всех сотрудников с указанным именем
    public List<User> findAllByFirstName(Session session, String firstName) {
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//        Root<User> user = criteria.from(User.class);
//        criteria.select(user).where(cb.equal(user.get("firstName"), firstName));
//        return session.createQuery(criteria).list();
        QUser user = QUser.user;
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.firstName.eq(firstName)).fetch();
    }

    //    Возвращает первые limit сотрудников, упорядоченных по дате рождения
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
//        return session.createQuery("SELECT u FROM User u ORDER BY u.birthdayDate", User.class)
//                .setMaxResults(limit)
//                .list();
        QUser user = QUser.user;
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.birthdayDate.asc())
                .limit(limit)
                .fetch();
    }

    //    Возвращет всех сотрудников компании с указанным названием
    public List<User> findAllByCompanyName(Session session, String companyName) {
//        return session.createQuery("SELECT u FROM User u WHERE u.company.name = :companyName", User.class)
//                .setParameter("companyName", companyName)
//                .list();
        QUser user = QUser.user;
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .join(user.company)
                .where(user.company.name.eq(companyName))
                .fetch();

    }

    /*
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
//        return session.createQuery("SELECT p FROM Payment p " +
//                        "JOIN p.receiver u " +
//                        "JOIN  u.company c " +
//                        "WHERE c.name = :companyName " +
//                        "ORDER BY u.firstName, p.amount", Payment.class)
//                .setParameter("companyName", companyName)
//                .list();

        QUser user = QUser.user;
        QPayment payment = QPayment.payment;
        QCompany company = QCompany.company;
        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user)
                .join(payment.receiver.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.firstName.asc(), payment.amount.asc())
                .fetch();

    }

    //    Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
    public Double findAvgPaymentAmountByFilter(Session session, Predicate predicate) {
//        return session.createQuery("SELECT AVG(p.amount) "+
//                        "FROM Payment p " +
//                        "JOIN p.receiver u " +
//                        "WHERE u.firstName = :firstName AND u.lastName = :lastName", Double.class)
//                .setParameter("firstName", firstName)
//                .setParameter("lastName", lastName)
//                .uniqueResult();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Double> criteria = cb.createQuery(Double.class);
//
//        Root<Payment> paymentRoot = criteria.from(Payment.class);
//        Join<Object, Object> user = paymentRoot.join("receiver");
//
//        List<Predicate> predicates = new ArrayList<>();
//        if (firstName != null) {
//            predicates.add(cb.equal(user.get("firstName"), firstName));
//        }
//        if (lastName != null) {
//            predicates.add(cb.equal(user.get("lastName"), lastName));
//        }
//
//        criteria.select(cb.avg(paymentRoot.get("amount")));
//        criteria.where(predicates.toArray(new Predicate[0]));
//
//        return session.createQuery(criteria).uniqueResult();
        QPayment payment = QPayment.payment;
        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, QUser.user)
                .where(predicate)
                .fetchOne();
    }

    //    Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return session.createQuery("SELECT c.name, AVG(p.amount) FROM Company c " +
                "JOIN c.users u " +
                "JOIN u.payments p " +
                "GROUP BY c.name " +
                "ORDER BY c.name", Object[].class).list();
    }


}
