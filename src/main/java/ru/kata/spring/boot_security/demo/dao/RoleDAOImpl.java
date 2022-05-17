package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> allRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Role findRoleByName(String roleName) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r from Role r where r.name = :roleName", Role.class);
        return query.setParameter("roleName", roleName).getSingleResult();
    }
}
