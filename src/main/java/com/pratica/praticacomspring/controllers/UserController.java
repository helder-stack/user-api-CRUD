package com.pratica.praticacomspring.controllers;

import com.pratica.praticacomspring.dtos.responses.UserDto;
import com.pratica.praticacomspring.models.User;
import com.pratica.praticacomspring.security.JWTUtil;
import com.pratica.praticacomspring.security.SecurityConfig;
import com.pratica.praticacomspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("")
    public UserDto create(@RequestBody() User user)  {
        return this.service.create(user);
    }

    @PostMapping("/auth")
    public Object auth(@RequestBody() User user){
        return this.service.auth(user.getEmail(), user.getPassword());
    }

    @GetMapping("")
    public List<UserDto> getUsers() throws Exception{
        return this.service.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Integer id){
        return this.service.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable("id") Integer id, @RequestBody() User user){
        return this.service.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Integer id){
        this.service.deleteUserById(id);
    }

}
