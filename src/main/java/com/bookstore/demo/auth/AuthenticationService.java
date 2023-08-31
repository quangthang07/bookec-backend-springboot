package com.bookstore.demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.demo.config.JwtService;
import com.bookstore.demo.user.Role;
import com.bookstore.demo.user.User;
import com.bookstore.demo.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .email(request.getEmail())
      .password(request.getPassword())
      .role(Role.USER)
      .build();
    String token = this.userService.registerUser(user);
    return AuthenticationResponse.builder()
      .token(token)
      .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    String token = this.userService.authenticate(request.getEmail());
    return AuthenticationResponse.builder()
      .token(token)
      .build();
  }
}
