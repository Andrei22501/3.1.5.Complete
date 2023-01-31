package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServices implements UserDetailsService {
    private final UserDao userDao;

    public UserDetailServices(UserDao userDao) {
        this.userDao = userDao;
    }
    private Collection<? extends GrantedAuthority> getRoleForUser(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User auth = userDao.getEmail(username);
        org.hibernate.Hibernate.initialize(auth.getRoles());
        return new org.springframework.security.core.userdetails.User(auth.getEmail(), auth.getPassword(),
                getRoleForUser(auth.getRoles()));
    }
}
