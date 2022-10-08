package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {

        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(
                            "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                                    "name VARCHAR(64), lastname VARCHAR(64), age TINYINT);")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(
                            "DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            user = session.get(User.class, user.getId());
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;

        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            userList = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
