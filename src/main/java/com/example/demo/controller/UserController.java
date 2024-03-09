package com.example.demo.controller;

import com.example.demo.Entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.response.Status;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/user")
    UserResponse register(@RequestBody User user) {
        UserResponse response = new UserResponse();
        try {
            User createdUser = service.register(user);
            response.setUsers(Collections.singletonList(createdUser));
            response.setStatus(new Status(1, "User registered successfully", "SUCCESS"));
            return response;
        } catch (CustomException e) {
            System.out.println(e);
            response.setStatus(e.getStatus());
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(1, "User registration failed", "ERROR"));
            return response;
        }
    }



    @PostMapping("/login")
    UserResponse login(@RequestBody User user) {
        UserResponse response = new UserResponse();
        try {
            User loggedInUser = service.login(user);
            response.setUsers(Collections.singletonList(loggedInUser));
            response.setStatus(new Status(2, "User log in successful", "SUCCESS"));
            return response;
        } catch (CustomException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            response.setStatus(e.getStatus());
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(4, "User login failed", "ERROR"));
            return response;
        }
    }

//    @GetMapping("/{fileReferenceId}")
//    FileResponse retrieveFile(@PathVariable String fileReferenceId) {
//        FileResponse response = new FileResponse();
//        try {
//            FileEntry file = service.getFile(fileReferenceId);
//            if(Objects.isNull(file)) {
//                response.setStatus("File not found");
//            } else {
//                response.setStatus("File retrieved successfully");
//                response.setData(Collections.singletonList(file));
//            }
//            return response;
//        } catch (Exception e) {
//            response.setStatus("Error occurred while retrieving file");
//            System.out.println(e);
//            return response;
//        }
//    }
}
