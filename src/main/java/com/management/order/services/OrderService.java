package com.management.order.services;

import org.springframework.stereotype.Service;

import com.management.order.entities.Order;
import com.management.order.repositories.OrderRepository;

@Service
public class OrderService extends GenericCRUDService<Order, Long> {

  public OrderService(OrderRepository repository) {
    super(repository);
  }

}
