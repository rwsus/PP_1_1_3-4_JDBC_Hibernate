package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(64), lastname VARCHAR(64), age TINYINT);");
            connection.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }

    }

    public void dropUsersTable() {
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users (name, lastname, age) VALUES ('" + user.getName() + "', '" +
                    user.getLastName() + "', " + user.getAge() + ");");
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }

    }

    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE id = " + user.getId());
            connection.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }
    }

    public List<User> getAllUsers() {
        List<User> userlist = new ArrayList<>();

        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, name, lastname, age FROM users;");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastname, age);
                user.setId(id);
                userlist.add(user);
            }
            connection.commit();
            return userlist;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(resultSet);
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }
    }

    public void cleanUsersTable() {
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users");
            connection.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }
    }
}
