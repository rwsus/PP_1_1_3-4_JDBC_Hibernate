package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Kolya", "Bykov", (byte) 29);
        userService.saveUser("Nastya", "Ponyala", (byte) 24);
        userService.saveUser("Vasya", "Lobkov", (byte) 26);
        userService.saveUser("Anton", "Kachin", (byte) 24);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
