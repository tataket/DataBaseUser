package com.mindera.users.service;

import com.mindera.users.entity.User;
import com.mindera.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UsersRepository repository;

    public UserService(UsersRepository repository) {
        this.repository = repository;
    }

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

    public User getUserById(Integer userId) {
        return repository.getReferenceById(userId);
    }

    public User updateUser(Integer userId, User user) {
        User userExist = repository.getReferenceById(userId);
        userExist.setUsername(user.getUsername());
        userExist.setPassword(user.getPassword());
        repository.save(userExist);
        return userExist;
    }

    public void deleteUser(Integer userId) {
        repository.deleteById(userId);
    }

    public User updateUserDetail(Integer userId, User user) {
        if (!user.getUsername().isEmpty()) {
            repository.getReferenceById(userId).setUsername(user.getUsername());
        }
        if (!user.getPassword().isEmpty()) {
            repository.getReferenceById(userId).setPassword(user.getPassword());
        }
        return repository.getReferenceById(userId);
    }
}
