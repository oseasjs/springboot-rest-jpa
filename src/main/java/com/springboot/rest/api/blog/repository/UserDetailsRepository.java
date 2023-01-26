package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
//public class UserDetailsRepository {
//
//  private List<UserDetails> APPLICATION_USERS = List.of(
//    new org.springframework.security.core.userdetails.User(
//      "user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//    ),
//    new org.springframework.security.core.userdetails.User(
//      "admin", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
//    )
//  );
//
//  public UserDetails findByUsername(String username) throws UsernameNotFoundException {
//    return APPLICATION_USERS
//      .stream()
//      .filter(u -> u.getUsername().equals(username))
//      .findFirst()
//      .orElseThrow(() -> new BlogBusinessException("User not found"));
//  }
//}
