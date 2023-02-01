package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public RoleDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> listRole() {
        return entityManager.createQuery("select u from Role u", Role.class)
                .getResultList();
    }

    @Override
    public Role getRoleId(int id) {
        String e = "from Role where id =" + id;
        TypedQuery<Role> query = entityManager.createQuery(e, Role.class);
        return query.getSingleResult();
    }
    @Override
    public Role getName(String name) {
        return entityManager.createQuery("select u from Role u where u.name = ?1", Role.class)
                .setParameter(1, name)
                .getSingleResult();
    }
    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }
}
