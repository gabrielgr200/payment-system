package com.leirbaggr.paymentsystem.controllers;


import com.leirbaggr.paymentsystem.dto.AuthenticationRequest;
import com.leirbaggr.paymentsystem.dto.AuthenticationResponse;
import com.leirbaggr.paymentsystem.entity.User;
import com.leirbaggr.paymentsystem.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()
        );
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
