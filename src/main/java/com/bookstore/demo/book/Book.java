package com.bookstore.demo.book;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {

  @TextIndexed(weight = 1)
  private String title;
  @TextIndexed(weight = 1)
  private List<String> authors;
  @TextIndexed
  private String category;
  @TextIndexed
  private String publisher;
  private @TextScore double score;

  private @Id BigInteger id;
  private String description;
  private Double price;
  private int stocks;
  private int soldAmount;
  private int pages;

}
