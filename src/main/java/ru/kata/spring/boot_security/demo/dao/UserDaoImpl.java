package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.TestUser;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;


import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<TestUser> users = allTestUser();

        System.out.println(users);


        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User addUser(User user) {
        Role roleUser = roleService.findRoleByName("USER");
        if (user.getRoles().isEmpty()) {
            user.addRole(roleUser);
        }
        entityManager.persist(user);
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        return null;
    }

    @Override
    public User editUser(Long id, User user) {
        entityManager.merge(user);
        return null;
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
    public User findUserById(Long id) {
        return entityManager.createQuery("select user from User user " +
                        "join fetch user.roles where user.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public String userPass(Long id) {
        TypedQuery<String> query = entityManager
                .createQuery("select u.password from User u where u.id = :userId", String.class);
        return query.setParameter("userId", id).getSingleResult();
    }

    @Override
    public List<TestUser> allTestUser() {

        EntityGraph<TestUser> entityGraph = entityManager.createEntityGraph(TestUser.class);
        entityGraph.addAttributeNodes("metadataUser");

        List<TestUser> testUserList = entityManager.createQuery("select tu from TestUser tu", TestUser.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();

        System.out.println(testUserList);


        return testUserList;
    }
}
