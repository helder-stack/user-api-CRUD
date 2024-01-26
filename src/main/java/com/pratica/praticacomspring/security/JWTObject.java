package com.pratica.praticacomspring.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JWTObject {
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<SimpleGrantedAuthority> getRoles(){
        return this.roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public void setRoles(List<String> roles){ this.roles = roles; }

    private String subject;
    private Date issuedAt;
    private Date expiration;
    private List<String> roles;

}
