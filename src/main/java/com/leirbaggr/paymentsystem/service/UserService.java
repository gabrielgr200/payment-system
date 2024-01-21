package com.leirbaggr.paymentsystem.service;

import com.leirbaggr.paymentsystem.dto.UserResponse;
import com.leirbaggr.paymentsystem.entity.User;
import com.leirbaggr.paymentsystem.repository.UserRepository;
import com.leirbaggr.paymentsystem.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email already exist");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnable(false);

            User savedUser = userRepository.save(user);
            UserResponse userResponse = new UserResponse(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getPassword()
            );

            mailService.sendVerificationEmail(user);
            return userResponse;
        }
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnable(true);
            userRepository.save(user);

            return true;
        }
    }
}
