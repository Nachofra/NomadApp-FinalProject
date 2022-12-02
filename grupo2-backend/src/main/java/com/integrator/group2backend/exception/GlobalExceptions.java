package com.integrator.group2backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> procesamientoResourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> procesamientoBadRequestException(BadRequestException exception){
        Map<String, Object> data = new HashMap<>();

        if(exception.getMessage().equals("Error verificating user")) {
            data.put("title", "We couldn't verificate this user");
            data.put("description", "User is already verificated or does not exist.");
        }

        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(data));
        }
        // Handle IOException of objectMapper.writeValueAsString
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ExceptionHandler(com.integrator.group2backend.exception.DataIntegrityViolationException.class)
    public ResponseEntity<String> procesamientoDataIntegrityViolationException(DataIntegrityViolationException exception){
        Map<String, Object> data = new HashMap<>();

        if(exception.getMessage().equals("The user is already created")) {
            data.put("title", "We couldn't create your account");
            data.put("description", "The email entered is already taken by another user. Please try again with another email.");
        }

        if(exception.getMessage().equals("The password length is less than 7")) {
            data.put("title", "We couldn't create your account");
            data.put("description", "The password entered is less than 7 characters long. Please try again with a longer password.");
        }

        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(data));
        }
        // Handle IOException of objectMapper.writeValueAsString
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
