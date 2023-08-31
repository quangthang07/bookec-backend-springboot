package com.bookstore.demo.author;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CRUDRequest {

  private BigInteger id;

  private String fullName;
  private List<BigInteger> books;
}
