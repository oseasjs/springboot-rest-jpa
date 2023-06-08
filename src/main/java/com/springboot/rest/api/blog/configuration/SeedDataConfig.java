package com.springboot.rest.api.blog.configuration;

import com.springboot.rest.api.blog.model.User;
import com.springboot.rest.api.blog.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {

        if (Arrays.stream(env.getActiveProfiles()).noneMatch(_env -> (_env.equalsIgnoreCase("test")))) {
            // Create default user in DB: username = username | password = password
            userDetailsService.save(new User(null, "username", "password"));
        }

    }

}
