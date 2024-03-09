package com.example.demo.respository;

import com.example.demo.Entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.response.Status;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public User create(User newUser) throws CustomException {
        Long maxUserId = 1L;
        for(User existingUser : this.users) {
            if(existingUser.getEmailId() == newUser.getEmailId()) {
                throw new CustomException(new Status(1, "User already present", "ERROR"));
            }

            maxUserId = Math.max(existingUser.getUserId(), maxUserId);
        }

        newUser.setUserId(maxUserId+1);
        users.add(newUser);
        return newUser;
    }

    public void update(User user) throws CustomException {
        for(User existingUser : this.users) {
            if(existingUser.getEmailId().equals(user.getEmailId()) && existingUser.getPassword().equals(user.getPassword())) {
                existingUser.setToken(user.getToken());
                return;
            }
        }
        throw new CustomException(new Status(2, "Username or password invalid", "ERROR"));
    }
}
