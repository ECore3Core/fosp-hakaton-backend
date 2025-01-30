package com.tinkov.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinkov.authentication.dto.IdResponse;
import com.tinkov.authentication.dto.RoleResponse;
import com.tinkov.authentication.dto.UserIdResponse;
import com.tinkov.authentication.dto.UserInfoResponse;
import com.tinkov.authentication.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileUser {

    @Autowired
    private final UserService userService;

    @GetMapping("/getIdByName/{username}")
    public ResponseEntity<IdResponse> getIdByName(@PathVariable String username) {
        return ResponseEntity.ok(new IdResponse(userService.getUseridByName(username)));
    }
    
    @GetMapping("/")
    public ResponseEntity<UserInfoResponse> getUserInfo(){
        return ResponseEntity.ok(new UserInfoResponse(userService.getCurrentUser().getUsername(), userService.getCurrentUser().getEmail()));
    }

    @GetMapping("/getUserRole")
    public ResponseEntity<RoleResponse> getUserRole() {
        return ResponseEntity.ok(new RoleResponse(userService.getRole()));
    }
    
    @GetMapping("/getIdByToken")
    public ResponseEntity<UserIdResponse> getIdByToken() {
        return ResponseEntity.ok(new UserIdResponse(userService.getCurrentUser().getId()));
    }
}
