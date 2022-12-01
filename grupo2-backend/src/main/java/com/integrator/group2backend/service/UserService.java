package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.CurrentUserDTO;
import com.integrator.group2backend.entities.User;
import com.integrator.group2backend.exception.DataIntegrityViolationException;
import com.integrator.group2backend.repository.UserRepository;
import com.integrator.group2backend.utils.MapperService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletResponse;
import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final MapperService mapperService;

    @Value("${fromAddress}")
    private String fromAddress;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, MapperService mapperService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.mapperService = mapperService;
    }

    public User addUser(User user, String siteURL) throws UnsupportedEncodingException, MessagingException, DataIntegrityViolationException {

        if (user.getPassword().length() < 7){
            throw new DataIntegrityViolationException("The password length is less than 7");
        }

        if(userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

//            sendVerificationEmail(user, siteURL);

            return userRepository.save(user);
        }else{
            throw new DataIntegrityViolationException("The user is already created");
        }
    }

    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String senderName = "Nomad App";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Nomad Support.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(this.fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());

        String verifyURL = siteURL + "/verify-confirmation?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }

    }

    public CurrentUserDTO getCurrentUser(String email) {
        User currentUser = this.userRepository.findByEmail(email);
        return this.mapperService.convert(currentUser, CurrentUserDTO.class);
    }
}
