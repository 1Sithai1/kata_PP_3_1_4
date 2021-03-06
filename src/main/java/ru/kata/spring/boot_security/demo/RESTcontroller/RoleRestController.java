package ru.kata.spring.boot_security.demo.RESTcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleRestController {

    private RoleService roleService;

    @Autowired
    public void setUserService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRestRoles() {
        return ResponseEntity.ok(roleService.allRoles());
    }

    @GetMapping("/roles/{role}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String role) {
        return ResponseEntity.ok(roleService.findRoleByName(role));
    }
}
