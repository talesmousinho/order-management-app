package com.management.order.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.order.entities.Order;
import com.management.order.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private static final Logger logger = LogManager.getLogger(OrderController.class);

  @Autowired
  private OrderService orderService;

  @GetMapping
  public ResponseEntity<List<Order>> findAll() {
    try {
      List<Order> orders = orderService.findAll();
      if (orders.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(orders);
    } catch (Exception e) {
      logger.error("Error ocurred while trying to find all orders: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Order> findById(@PathVariable Long id) {
    try {
      Order order = orderService.findById(id);
      return ResponseEntity.ok(order);

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to find an order: " + e.getMessage());
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      logger.error("Error ocurred while trying to find an order: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<Order> create(@RequestBody Order order) {
    try {
      order.setId(null);
      return ResponseEntity.ok(orderService.save(order));
    } catch (Exception e) {
      logger.error("Error ocurred while trying to create an order: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
    try {
      // check if order exists
      orderService.findById(id);
      // update order
      order.setId(id);
      return ResponseEntity.ok(orderService.save(order));

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to update an order: " + e.getMessage());
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      logger.error("Error ocurred while trying to update an order: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    try {
      orderService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error("Error ocurred while trying to delete an order: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
