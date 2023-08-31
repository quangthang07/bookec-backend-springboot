package com.bookstore.demo.order;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookstore.demo.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public List<Order> getAllOrder() {
    return this.orderRepository.findAll();
  }

  public boolean saveNewOrder(Order order) {
    if (orderRepository.findById(order.getId()).isPresent()) {
      return false;
    }
    if (order.getUser() == null || !order.getUser().equals(getRequestPerson())) {
      return false;
    }
    orderRepository.save(order);
    return true;
  }

  public boolean cancelOrder(BigInteger orderId) {
    Order existedOrder = orderRepository.findById(orderId).get();
    if (existedOrder == null) {
      return false;
    }
    if (!existedOrder.getUser().equals(getRequestPerson())) {
      return false;
    }
    existedOrder.setCanceled(true);
    orderRepository.save(existedOrder);
    return true;

  }

  private String getRequestPerson() {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user.getEmail();
  }
}
