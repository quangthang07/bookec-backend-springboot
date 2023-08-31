package com.bookstore.demo.order;

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
public class OrderRequest {

  private BigInteger id;
  private String user; // email
  private Double totalPrice;
  private List<OrderItem> details;
  private boolean canceled;

}
