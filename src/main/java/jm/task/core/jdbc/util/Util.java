package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Util {
    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        Properties properties = new Properties();

        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Can't get login db properties");
            throw new RuntimeException();
        }

        String hostName = properties.getProperty("hostName");
        String dbName = properties.getProperty("dbName");
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");

        Connection connection = getMySQLConnection(hostName, dbName, userName, password);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);
        return connection;
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        return DriverManager.getConnection(connectionURL, userName,
                password);
    }

    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                //NOP
            }
        }
    }
    public static void closeQuietly(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                //NOP
            }
        }
    }
    public static void closeQuietly(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //NOP
            }
        }
    }
    public  static  void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                //NOP
            }
        }
    }
}
