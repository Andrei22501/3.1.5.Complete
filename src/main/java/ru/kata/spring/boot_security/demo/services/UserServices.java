package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserServices {
    List<User> listUsers();

    User getEmail(String email);

    void update(int id, User user);

    User save(User user);

    User getUserById(int id);

    void delete(int id);
}
