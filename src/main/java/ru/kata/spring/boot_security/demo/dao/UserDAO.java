package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> allUsers();

    User addUser(User user);

    User deleteUser(Long id);

    User editUser(Long id, User user);

    User getUserId(Long id);

    User findByUsername(String username);

    User findUserById(Long id);

    String userPass(Long id);
}
