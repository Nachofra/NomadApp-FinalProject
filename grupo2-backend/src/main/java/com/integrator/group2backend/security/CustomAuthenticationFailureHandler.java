package com.integrator.group2backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrator.group2backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    public CustomAuthenticationFailureHandler() {
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
                "message",
                exception.getMessage());
        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
