package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        Role roleUser = roleService.findRoleByName("USER");
        if (user.getRoles().isEmpty()) {
            user.addRole(roleUser);
        }
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void editUser(Long id, User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserId(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u where u.email = :username", User.class);
        User user = query.setParameter("username", username).getSingleResult();
        user.getAuthorities().size();
        return user;
    }

    @Override
    public User findUserByID(Long id) {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u where u.id = :userId", User.class);
        return query.setParameter("userId", id).getSingleResult();
    }

    public String userPass(Long id) {
        TypedQuery<String> query = entityManager
                .createQuery("select u.password from User u where u.id = :userId", String.class);
        return query.setParameter("userId", id).getSingleResult();
    }
}
