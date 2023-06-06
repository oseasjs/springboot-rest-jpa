package com.springboot.rest.api.blog.security;

import com.springboot.rest.api.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.List;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUserSecurity> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserSecurity annotation) {

        List<? extends GrantedAuthority> authorities = Arrays.stream(annotation.authorities())
            .map(SimpleGrantedAuthority::new)
            .toList();
        
        User principal = User
            .builder()
            .username(annotation.username())
            .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserAuthenticationToken(principal));

        return context;

    }
    
}
