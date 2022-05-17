package ru.kata.spring.boot_security.demo.RESTcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

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
