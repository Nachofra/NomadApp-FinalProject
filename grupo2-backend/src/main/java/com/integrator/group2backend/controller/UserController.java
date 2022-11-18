package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.UserVerifyCodeDTO;
import com.integrator.group2backend.entities.User;
import com.integrator.group2backend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${frontendUrl}")
    private String frontendUrl;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody UserVerifyCodeDTO userVerifyCodeDTO) {
        if (userService.verify(userVerifyCodeDTO.getCode())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) throws UnsupportedEncodingException, MessagingException {
        return new ResponseEntity<>(userService.addUser(user, frontendUrl), HttpStatus.CREATED);
    }
}
