package com.integrator.group2backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptions {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> procesamientoResourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> procesamientoMailSendException(MailSendException exception){
        Map<String, Object> data = new HashMap<>();

        if(Objects.equals(exception.getMessage(), "The email is not a valid address")) {
            data.put("title", "We couldn't create your account");
            data.put("description", "The entered email is not a valid address. Please try again with another email");
        }

        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(data));
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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

        if(exception.getMessage().equals("The dates cannot be equal")) {
            data.put("title", "We couldn't create your reservation");
            data.put("description", "The dates entered cannot be the same. Please try again with other dates.");
        }

        if(exception.getMessage().equals("The range of dates is already taken")) {
            data.put("title", "We couldn't create your reservation");
            data.put("description", "The dates entered are already taken by another user. Please try again with other dates.");
        }

        if(exception.getMessage().equals("The dates are chronologically invalid")) {
            data.put("title", "We couldn't create your reservation");
            data.put("description", "The dates entered occurs before the actual date or are chronologically invalid. Please try again with other dates.");
        }

        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(data));
        }
        // Handle IOException of objectMapper.writeValueAsString
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @ExceptionHandler({DateParseException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> procesamientoDateParseException(Exception exception){
        Map<String, Object> data = new HashMap<>();

        if(exception.getMessage().equals("An error ocurred parsing dates") || exception.getMessage().contains("JSON parse error: Cannot deserialize value of type `java.util.Date`")) {
            data.put("title", "We couldn't create your reservation");
            data.put("description", "The dates entered are invalid. Please try again with other dates.");
        }

        try {
            if(!data.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(data));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        // Handle IOException of objectMapper.writeValueAsString
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
