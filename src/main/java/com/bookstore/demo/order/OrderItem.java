package com.bookstore.demo.order;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
  private BigInteger bookId;
  private String bookName;
  private int quantity;
  private double price;
}
