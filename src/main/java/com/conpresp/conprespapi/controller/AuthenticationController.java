package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.auth.AuthRequest;
import com.conpresp.conprespapi.dto.auth.AuthResponse;
import com.conpresp.conprespapi.service.TokenService;
import com.conpresp.conprespapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        String jwt = tokenService.generateToken(auth);

        var user = userService.getUserById(tokenService.getUserId(jwt)).orElse(null);

        assert user != null;
        return ResponseEntity.ok(new AuthResponse(jwt, "Bearer", user.getProfile().getName()));
    }
}
