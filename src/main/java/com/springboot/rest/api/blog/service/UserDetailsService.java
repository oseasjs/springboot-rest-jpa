package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.exception.BlogBusinessException;
import com.springboot.rest.api.blog.model.User;
import com.springboot.rest.api.blog.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
  @Autowired
  private UserDetailsRepository userDetailsRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public User save(User user) {

    Optional
      .ofNullable(userDetailsRepository.findByUsername(user.getUsername()))
      .ifPresent(u -> {
        throw new BlogBusinessException("User already exists");
      });

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userDetailsRepository.save(user);

  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional
      .ofNullable(userDetailsRepository.findByUsername(username))
      .orElseThrow(() -> new BlogBusinessException("User not found"));
  }
}
