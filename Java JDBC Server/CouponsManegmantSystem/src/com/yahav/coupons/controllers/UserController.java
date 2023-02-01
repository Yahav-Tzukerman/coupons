package com.yahav.coupons.controllers;

import com.yahav.coupons.beans.User;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.UserLogic;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final UserLogic userLogic = new UserLogic();

    public UserController() {
    }

    public void updateUser(User user) {
        try {
            int result = userLogic.updateUser(user);
            if (result != 0) {
                System.out.println(user.getUsername() + " Was updated successfully!");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUser(String username) {
        User user = null;
        try {
            user = userLogic.getUser(username);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User getUser(int userId) {
        User user = null;
        try {
            user = userLogic.getUser(userId);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public List<User> getUsersByType(UserType userType) {
        List<User> users = new ArrayList<>();
        try {
            users = userLogic.getUsersByType(userType);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = userLogic.getUsers();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void deleteUser(User user) {
        deleteUser(user.getUsername());
    }

    public void deleteUser(String username) {
        try {
            int result = userLogic.deleteUser(username);
            if (result != 0) {
                System.out.println(username + ": User was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        try {
            int result = userLogic.deleteUser(userId);
            if (result != 0) {
                System.out.println(userId + ": User was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer addUser(User user) {
        Integer userId = null;
        try {
            userId = userLogic.addUser(user);
            if (userId > 0) {
                System.out.println(user.getUsername() + ": User was added successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    public boolean login(String username, String password) {
        boolean login = false;
        try {
            login = userLogic.login(username, password);
            if (login) {
                System.out.println(username + " was login successfully into the server");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return login;
    }
}