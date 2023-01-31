package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            Set<Role> adminRole = new HashSet<>();
            adminRole.add(admin);
            adminRole.add(user);
            Set<Role> userRole = new HashSet<>();
            userRole.add(user);

            userServices.save(new User("admin", "admin", 21, "admin@gmail.com", "admin", adminRole));
            userServices.save(new User("user", "user", 24, "user@gmail.com", "user", userRole));
        }
    }
}
