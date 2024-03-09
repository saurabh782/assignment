package com.example.demo.service;

import com.example.demo.Entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User register(User user) throws CustomException {
        User returnedUser = repository.create(user);
        return returnedUser;
    }

    public User login(User user) throws CustomException {
        user.setToken(UUID.randomUUID().toString());
        repository.update(user);
        return user;
    }
}
