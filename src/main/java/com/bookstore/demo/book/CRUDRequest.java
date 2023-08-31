package com.bookstore.demo.book;

import java.math.BigInteger;
import java.util.List;

import com.mongodb.lang.NonNull;

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
  private String title;
  private String description;
  private String category;
  private String publisher;
  private Double price;
  private int stocks;
  private int soldAmount;
  private List<String> authors;
  private int pages;
}
