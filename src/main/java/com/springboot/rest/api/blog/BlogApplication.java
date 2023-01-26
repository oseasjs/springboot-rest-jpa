package com.springboot.rest.api.blog;

import com.springboot.rest.api.blog.model.User;
import com.springboot.rest.api.blog.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.springboot.rest.api.blog.feign.client")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class BlogApplication implements CommandLineRunner {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public void run(String... args) throws Exception {

    // Create default user in DB:
    // username = username
    // password = password
    userDetailsService.save(
      new User(null, "username", "password")
    );
  }

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }

}
