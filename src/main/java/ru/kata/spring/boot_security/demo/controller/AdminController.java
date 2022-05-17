package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private RoleService roleService;

    @Autowired
    public void setUserService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUser(Model model, Principal principal) {
        model.addAttribute("userPrincipal", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roleList", roleService.allRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PatchMapping("/admin/edit/{id}")
    public String update(@ModelAttribute("editUser")
                         User user,
                         @RequestParam(value = "role", required = false) String[] roles,
                         @PathVariable("id") Long id) {
        Set<Role> roleSet = new HashSet<>();
        if (roles == null) {
            user.setRoles(userService.getUserId(id).getRoles());
        } else {
            for (String role : roles) {
                roleSet.add(roleService.findRoleByName(role));
                user.setRoles(roleSet);
            }
        }
        userService.editUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/newUser")
    public String create(@ModelAttribute("newUser") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
}
