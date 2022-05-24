package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;


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

//      Данный метод необходим для работы без РЕСТ сервисов
//    @Override
//    public void editUser(Long id, User user) {
//        if (user.getRoles().isEmpty()) {
//            user.setRoles(getUserId(id).getRoles());
//        }
//        Set<Role> idRole = user.getRoles();
//        Set<Role> newRole = new HashSet<>();
//        for (Role role : idRole) {
//            newRole.add(findRoleById(Long.valueOf(role.getName())));
//        }
//        user.setRoles(newRole);
//        entityManager.merge(user);
//    }

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
}
