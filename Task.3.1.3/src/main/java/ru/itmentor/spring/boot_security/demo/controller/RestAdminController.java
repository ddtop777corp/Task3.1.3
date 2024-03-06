package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import java.util.Optional;

@RestController
public class RestAdminController {
    private final UserService userService;
    private final RoleService roleService;

    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/getAuthorizedUser")
    public ResponseEntity<User> getAuthorizedUser() {
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(authorizedUser);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteById(id);

        return new ResponseEntity<>("Пользователь с id = " + id + " удален.", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

}