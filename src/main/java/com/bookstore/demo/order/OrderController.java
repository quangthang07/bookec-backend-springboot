package com.bookstore.demo.order;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<Object> getAllOrder() {
    List<Order> orders = orderService.getAllOrder();
    if(orders.isEmpty()) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Not found");
    }
    return ResponseEntity.ok(orders);
  }

  @PostMapping
  public ResponseEntity<Object> saveNewOrder(@RequestBody OrderRequest orderRequest) {
    Order order = Order.builder()
      .user(orderRequest.getUser())
      .totalPrice(orderRequest.getTotalPrice())
      .details(orderRequest.getDetails())
      .isCanceled(false)
      .build();

    if (orderService.saveNewOrder(order)) {
      return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Successful");
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Not allowed");
  }

  @PutMapping
  public ResponseEntity<Object> cancelOrder(@RequestBody BigInteger orderId) {
    if (orderService.cancelOrder(orderId)) {
      return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Successful");
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Not allowed");
  }
}
