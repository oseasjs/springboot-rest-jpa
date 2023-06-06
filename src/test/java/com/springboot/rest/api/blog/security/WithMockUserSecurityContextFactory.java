package com.springboot.rest.api.blog.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.springboot.rest.api.blog.model.User;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUserSecurity> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserSecurity annotation) {

        List<? extends GrantedAuthority> authorities = Arrays.stream(annotation.authorities())
            .map(SimpleGrantedAuthority::new)
            .toList();
        
        UserDetails principal = User
            .builder()
            .id(annotation.userId())
            .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new AnonymousAuthenticationToken(null, principal, authorities));

        return context;

    }
    
}
