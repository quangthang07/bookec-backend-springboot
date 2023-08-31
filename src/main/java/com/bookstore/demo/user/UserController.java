package com.bookstore.demo.user;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<Object> getAllUsers() {
    List<User> userList = this.userService.getAllUsers();
    List<UserResponse> response = new ArrayList<UserResponse>();
    for(User user : userList) {
      response.add(
        UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole())
        .build()
      );
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable BigInteger id) {
    var user = this.userService.getUserById(id);
    if (user == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("User not found.");
    }
    return ResponseEntity.ok(
      UserResponse.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole())
        .build()
    );
  }

  @PostMapping
  public ResponseEntity<Object> registerNewUser(@RequestBody User user) {
    var addedUser = this.userService.addNewUser(user);
    if (addedUser == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Email is already used.");
    }
    return ResponseEntity.ok(addedUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUser(@PathVariable BigInteger id, @RequestBody User user) {
    var updatedUser = this.userService.updateUser(id, user);
    if (updatedUser == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("User not found.");
    }
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable BigInteger id) {
    var deletedUser = this.userService.deleteUser(id);
    if (deletedUser == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("User not found.");
    }
    return ResponseEntity.ok(deletedUser);
  }
}
