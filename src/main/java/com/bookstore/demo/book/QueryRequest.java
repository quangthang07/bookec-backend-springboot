package com.bookstore.demo.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {

  private String title;
  private String category;
  private String publisher;
  private Double maxPrice;
  private Double minPrice;
}
