package com.springboot.rest.api.blog.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUserSecurity {

    long userId() default 1l;
    String[] authorities() default "ROLE_USER";

    
}
