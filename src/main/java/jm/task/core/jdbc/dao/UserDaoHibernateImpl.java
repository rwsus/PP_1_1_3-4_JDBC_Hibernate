package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(
                            "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                                    "name VARCHAR(64), lastname VARCHAR(64), age TINYINT);")
                    .executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(
                            "DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            user = session.get(User.class, user.getId());
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            userList = session.createSQLQuery("SELECT *FROM users").getResultList();
            session.getTransaction().commit();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users");
            session.getTransaction().commit();
        }

    }
}
