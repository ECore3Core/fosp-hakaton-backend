package com.tinkov.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinkov.authentication.dto.IsValidTokenResponse;
import com.tinkov.authentication.dto.JwtAuthenticationResponse;
import com.tinkov.authentication.dto.SignInRequest;
import com.tinkov.authentication.dto.SignUpRequest;
import com.tinkov.authentication.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class Authentication {
    
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/validate-token")
    public JwtAuthenticationResponse getToken(@RequestHeader("Authorization") String token){
        return authenticationService.tokenRefresh(token);
    }

    @GetMapping("/is-token-valid")
    public IsValidTokenResponse isTokenValid(@RequestHeader("Authorization") String token){
        return new IsValidTokenResponse(authenticationService.isTokenValid(token));
    }
}
