package com.integrator.group2backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrator.group2backend.entities.User;
import com.integrator.group2backend.repository.UserRepository;
import com.integrator.group2backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;


    public CustomAuthenticationFailureHandler( UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public CustomAuthenticationFailureHandler() {
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        User credentials = this.objectMapper.readValue(request.getInputStream(), User.class);
        User user = this.userRepository.findByEmail(credentials.getEmail());
        if(user == null){
            response.getWriter().write("Credenciales Incorrectas");
        }
        if(!credentials.isEnabled()){
            response.getWriter().write("Correo no verificado");
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
