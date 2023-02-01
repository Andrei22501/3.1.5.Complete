package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class init {
    private final RoleServices roleServices;
    private final UserServices userServices;

    @Autowired
    public init(RoleServices roleServices, UserServices userServices) {
        this.roleServices = roleServices;
        this.userServices = userServices;
    }

    @PostConstruct
    public void postConstruct() {
        List<User> users = userServices.listUsers();
        if (users.isEmpty()) {
            Role user = new Role("ROLE_USER");
            Role admin = new Role("ROLE_ADMIN");
            roleServices.save(admin);
            roleServices.save(user);

            User adminAccount = new User();
            adminAccount.setName("Admin");
            adminAccount.setLastname("Admin");
            adminAccount.setAge(21);
            adminAccount.setEmail("admin@gmail.com");
            adminAccount.setPassword("admin");
            adminAccount.setRoles(List.of(admin, user));
            userServices.save(adminAccount);
        }
    }
}
