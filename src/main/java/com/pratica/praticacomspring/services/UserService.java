package com.pratica.praticacomspring.services;

import com.pratica.praticacomspring.dtos.responses.UserDto;
import com.pratica.praticacomspring.handlers.BadRequestException;
import com.pratica.praticacomspring.handlers.ForbiddenException;
import com.pratica.praticacomspring.handlers.NotFoundException;
import com.pratica.praticacomspring.models.User;
import com.pratica.praticacomspring.repositories.UserRepository;
import com.pratica.praticacomspring.security.JWTObject;
import com.pratica.praticacomspring.security.JWTUtil;
import com.pratica.praticacomspring.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    UserRepository repository;

    public UserDto create(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(this.userExists(user.getEmail())){
            throw new BadRequestException("User already exists");
        }else{
            return new UserDto(this.repository.save(user));
        }
    }

    public List<UserDto> getUsers(){
        List<User> users = this.repository.findAll();
        return users.stream().map(UserDto::new).toList();
    }

    public UserDto getUserById(Integer id){
        Optional<User> userOptional = this.repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDto(user);
        } else {
            throw new NotFoundException("User");
        }
    }

    public UserDto updateUserById(Integer id, User user){
        Optional<User> currentUser = this.repository.findById(id);
        if(currentUser.isPresent()){
            User updatedUser = currentUser.get();
            if(!Objects.equals(user.getEmail(), currentUser.get().getEmail())){
                if(this.userExists(user.getEmail())) {
                    throw new BadRequestException("The email is already in use");
                }
                updatedUser.setEmail(user.getEmail());
            }
            updatedUser.setName(user.getName());
            updatedUser.setPassword(user.getPassword());
            return new UserDto(this.repository.save(updatedUser));
        }else{
            throw new NotFoundException("User");
        }
    }

    public void deleteUserById(Integer id){
        Optional<User> currentUser = this.repository.findById(id);
        if(currentUser.isPresent()){
            this.repository.deleteById(id);
        }else{
            throw new NotFoundException("User");
        }
    }

    public String auth(String email, String password){
        Optional<User> user = this.repository.findByEmail(email);
        if(user.isPresent()){
            if(this.validatePassword(password, user.get().getPassword())){
                return jwtUtil.generateToken(user.get());
            }else{
                throw new ForbiddenException("Password is not valid");
            }
        }else{
            throw new NotFoundException("User");
        }
    }

    private Boolean validatePassword(String password, String userPassword){
        return new BCryptPasswordEncoder().matches(password, userPassword);
    }

    private Boolean userExists(String email){
        Optional<User> user = this.repository.findByEmail(email);
        return user.isPresent();
    }
}
