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

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            throw new RuntimeException(e);
        }
        try {
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE users ('id' BIGINT PRIMARY KEY AUTO_INCREMENT, 'name' VARCHAR(64), " +
                        "'lastname' VARCHAR(64), 'age' TINYINT);");


        connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(resultSet);
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }

    }

    public void dropUsersTable() {
        try {
            connection = Util.getMySQLConnection();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            throw new RuntimeException(e);
        }
        try {
            statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE users;");


            connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(resultSet);
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        List<User> userlist = new ArrayList<>();

        try {
            connection = Util.getMySQLConnection();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with connection to database");
            throw new RuntimeException(e);
        }
        try {
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

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            throw new RuntimeException(e);

        } finally {
            Util.closeQuietly(resultSet);
            Util.closeQuietly(statement);
            Util.closeQuietly(connection);
       }
    }

    public void cleanUsersTable() {

    }
}
