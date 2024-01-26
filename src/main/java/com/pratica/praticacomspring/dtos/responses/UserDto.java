package com.pratica.praticacomspring.dtos.responses;

import com.pratica.praticacomspring.models.User;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class UserDto {
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    private Integer id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
