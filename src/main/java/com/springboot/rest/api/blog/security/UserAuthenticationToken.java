package com.springboot.rest.api.blog.security;

import com.springboot.rest.api.blog.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final User principal;

    public UserAuthenticationToken(User principal) {
        super(principal.getAuthorities());
        this.principal = principal; 
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; 
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
    
}
