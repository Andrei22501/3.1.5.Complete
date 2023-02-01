package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImp implements UserServices {
    private final UserDao userDao;
    private final RoleServices roleServices;

    @Autowired
    public UserServicesImp(UserDao userDao, RoleServices roleServices) {
        this.userDao = userDao;
        this.roleServices = roleServices;
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserByID(id);
    }

    @Override
    public User getEmail(String email) {
        return userDao.getEmail(email);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(user.getRoles().stream().map(role -> roleServices.getName(role.getName())).collect(Collectors.toList()));
        userDao.update(user);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(user.getRoles().stream().map(role -> roleServices.getName(role.getName())).collect(Collectors.toList()));
        userDao.save(user);
        return user;
    }

}