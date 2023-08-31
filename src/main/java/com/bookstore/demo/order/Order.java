package com.bookstore.demo.order;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("orders")
public class Order {

  private BigInteger id;
  private String user; // email
  private Double totalPrice;
  private List<OrderItem> details;
  private boolean isCanceled;
}
