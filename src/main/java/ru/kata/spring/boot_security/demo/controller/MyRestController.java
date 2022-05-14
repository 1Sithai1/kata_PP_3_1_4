package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin")
    public List<User> allRestUsers() {
        return userService.allUsers();
    }

    @GetMapping("/admin/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserByID(id);
    }

    @GetMapping("/user")
    public User getUserbyName(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping("/roles")
    public List<Role> allRestRoles() {
        return userService.allRoles();
    }

    @GetMapping("/roles/{role}")
    public Role getRoleByName(@PathVariable String role) {
        return userService.findRoleByName(role);
    }

    @PostMapping()
    public void newUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PatchMapping("/admin/{id}")
    public void editUser(@PathVariable ("id") Long id,@RequestBody User user ) {
        userService.editUser(id, user);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteUser(@PathVariable ("id") Long id) {
        userService.deleteUser(id);
    }
}
