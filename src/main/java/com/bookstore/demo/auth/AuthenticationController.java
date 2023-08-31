package com.bookstore.demo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<Object> register(
    @RequestBody RegisterRequest request
  ) {
    if(authenticationService.register(request).getToken().isEmpty()) {
      return ResponseEntity.badRequest().body("Email was already used.");
    }
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping(value="/authenticate")
  public ResponseEntity<Object>  postMethodName(@RequestBody AuthenticationRequest request) {
    if(authenticationService.authenticate(request).getToken().isEmpty()) {
      return ResponseEntity.badRequest().body("Email or Password not found.");
    }
    return ResponseEntity.ok(authenticationService.authenticate(request));

  }
  
}
