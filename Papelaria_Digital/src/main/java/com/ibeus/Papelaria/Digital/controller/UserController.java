package com.ibeus.Papelaria.Digital.controller;

import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{login}")
    public Optional<User> getUserByLogin(@PathVariable String login) {
        return userService.findUserByLogin(login);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
