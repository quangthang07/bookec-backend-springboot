package com.bookstore.demo.review;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("reviews")
public class Review {

  @Id
  private BigInteger id;

  private BigInteger bookId;
  private String user;
  private Double rate;
  private String content;
}
