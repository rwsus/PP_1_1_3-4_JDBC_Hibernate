package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
// using UserDaoHibernateImpl
public class UserServiceImpl implements UserService {
    private final UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }
}

// using UserDaoJDBCImpl

//    private UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
//
//    public void createUsersTable() {
//        userDaoJDBC.createUsersTable();
//    }
//
//    public void dropUsersTable() {
//        userDaoJDBC.dropUsersTable();
//    }
//
//    public void saveUser(String name, String lastName, byte age) {
//        userDaoJDBC.saveUser(name, lastName, age);
//    }
//
//    public void removeUserById(long id) {
//        userDaoJDBC.removeUserById(id);
//    }
//
//    public List<User> getAllUsers() {
//        return userDaoJDBC.getAllUsers();
//    }
//
//    public void cleanUsersTable() {
//        userDaoJDBC.cleanUsersTable();
//    }
//}
