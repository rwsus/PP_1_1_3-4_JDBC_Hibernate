package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {

    private static Util instance;       // make Util singleton
    private static final String url = "jdbc:mysql://localhost:3306/dbForzad113";
    private static final String username = "root";
    private static final String pass = "mSql026335";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory;


    private Util() {}

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
    // Connect to MySQL
    public static Connection getMySQLConnection() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, pass);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //hibernate connection

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties hiberprops = new Properties();

            hiberprops.put(Environment.DRIVER, driver);
            hiberprops.put(Environment.URL, url);
            hiberprops.put(Environment.USER, username);
            hiberprops.put(Environment.PASS, pass);
            hiberprops.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            hiberprops.put(Environment.SHOW_SQL, "true");
            hiberprops.put(Environment.FORMAT_SQL, "true");
            hiberprops.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            hiberprops.put(Environment.HBM2DDL_AUTO, "");
            hiberprops.put(Environment.DEFAULT_SCHEMA, "dbForzad113");
            configuration.setProperties(hiberprops);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            try {
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
                throw new RuntimeException("Building sessionFactory failed");
            }
        }
        return sessionFactory;
    }
}