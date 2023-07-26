package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Payment;
import org.example.entity.User;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {
    public static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    //    Возвращает всех сотрудников
    public List<User> findAll(Session session) {
        return session.createQuery("SELECT u FROM User u", User.class).list();
    }

    //    Возвращает всех сотрудников с указанным именем
    public List<User> findAllByFirstName(Session session, String firstName) {
        return session.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    //    Возвращает первые limit сотрудников, упорядоченных по дате рождения
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("SELECT u FROM User u ORDER BY u.birthdayDate", User.class)
                .setMaxResults(limit)
                .list();
    }

    //    Возвращет всех сотрудников компании с указанным названием
    public List<User> findAllByCompanyName(Session session, String companyName) {
        return session.createQuery("SELECT u FROM User u WHERE u.company.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    /*
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("SELECT p FROM Payment p " +
                        "JOIN p.receiver u " +
                        "JOIN  u.company c " +
                        "WHERE c.name = :companyName " +
                        "ORDER BY u.firstName, p.amount", Payment.class)
                .setParameter("companyName", companyName)
                .list();
    }

    //    Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("SELECT AVG(p.amount) " +
                        "FROM Payment p " +
                        "JOIN p.receiver u " +
                        "WHERE u.firstName = :firstName AND u.lastName = :lastName", Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();
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
