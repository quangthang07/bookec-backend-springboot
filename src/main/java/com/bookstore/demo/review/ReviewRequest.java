package com.bookstore.demo.review;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

  private BigInteger id;
  private BigInteger bookId;
  private String user;
  private Double rate;
  private String content;
}
