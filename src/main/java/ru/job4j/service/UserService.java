package ru.job4j.service;

import ru.job4j.dao.UserDAO;
import ru.job4j.model.User;

public class UserService {

    private final UserDAO userDAO = UserDAO.instOf();

    private UserService() {

    }

    private static final class UserServiceHolder {
        private static final UserService USER_SERVICE = new UserService();
    }

    public static UserService instOf() {
        return UserServiceHolder.USER_SERVICE;
    }

    public User addUser(User user) {
        return userDAO.addUser(user);
    }

    public User findUserByName(String name) {
        return userDAO.findUserByName(name);
    }
}
