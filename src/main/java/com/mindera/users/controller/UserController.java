package com.mindera.users.controller;

import com.mindera.users.entity.User;
import com.mindera.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long  userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long  userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long  userId) {
        userService.deleteUser(userId);
    }

    @PatchMapping("/{userId}")
    public void updateUserDetail(@PathVariable Long  userId, @RequestBody User user) {
        userService.updateUserDetail(userId, user);
    }
}

