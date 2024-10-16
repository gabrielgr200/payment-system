package com.leirbaggr.paymentsystem.controllers;

import com.leirbaggr.paymentsystem.dto.AuthenticationRequest;
import com.leirbaggr.paymentsystem.dto.AuthenticationResponse;
import com.leirbaggr.paymentsystem.dto.UserRequest;
import com.leirbaggr.paymentsystem.dto.UserResponse;
import com.leirbaggr.paymentsystem.entity.User;
import com.leirbaggr.paymentsystem.service.TokenService;
import com.leirbaggr.paymentsystem.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserControllers {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);

        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/teste")
    public String teste() {
        return "Você está logado";
    }
}
