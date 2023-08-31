package com.bookstore.demo.user;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private BigInteger id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
}
