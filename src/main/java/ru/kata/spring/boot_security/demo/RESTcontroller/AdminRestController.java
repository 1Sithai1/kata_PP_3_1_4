package ru.kata.spring.boot_security.demo.RESTcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> allRestUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/admin/{id}")
    @ResponseBody
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping()
    public ResponseEntity<User> newUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<User> editUser(@PathVariable ("id") Long id,@RequestBody User user ) {
       return ResponseEntity.ok(userService.editUser(id, user));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable ("id") Long id) {
       return ResponseEntity.ok(userService.deleteUser(id));
    }
}
