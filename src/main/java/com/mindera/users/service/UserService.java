package com.mindera.users.service;

import com.mindera.users.entity.User;
import com.mindera.users.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UsersRepository repository;

    public List<User> getUsers() {
        return repository.findAll().stream().toList();
    }

    public User addUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        repository.save(newUser);
        return newUser;
    }

    public User getUserById(Long userId) {
        return repository.getReferenceById(userId);
    }

    public User updateUser(Long userId, User user) {
        User userExist = repository.getReferenceById(userId);
        userExist.setUsername(user.getUsername());
        userExist.setPassword(user.getPassword());
        repository.save(userExist);
        return userExist;
    }

    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }

    public void updateUserDetail(Long userId, User user) {
        if (repository.existsById(userId)) {
            User userExist = repository.findById(userId).orElse(null);
            if (user.getUsername() != null) {
                assert userExist != null;
                userExist.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                assert userExist != null;
                userExist.setPassword(user.getPassword());
            }
            assert userExist != null;
            repository.save(userExist);
        }
    }
}
