package com.management.order.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.management.order.entities.Item;
import com.management.order.entities.Order;
import com.management.order.entities.StockMovement;
import com.management.order.repositories.OrderRepository;
import com.management.order.repositories.StockMovementRepository;

@Service
public class OrderService extends GenericCRUDService<Order, Long> {

  @Autowired
  private StockMovementRepository stockMovementRepository;

  public OrderService(OrderRepository orderRepository) {
    super(orderRepository);
  }

  @Override
  @Transactional
  public Order save(Order order) {
    Item requiredItem = order.getItem();
    int requiredQuantity = order.getQuantity();

    // find all available stock for the item
    List<StockMovement> stockMovements = stockMovementRepository.findAvailableStockForItem(requiredItem);

    for (StockMovement stockMovement : stockMovements) {
      // if the order is fulfilled, break the loop
      if (requiredQuantity <= 0) {
        break;
      }

      int availableQuantity = stockMovement.getQuantity();
      int remainingQuantity = 0;
      // if there's less or equal available quantity
      if (availableQuantity <= requiredQuantity) {
        requiredQuantity -= availableQuantity;
      } else {
        // if there's more available quantity, duplicates the stock movement with the remaining quantity
        remainingQuantity = availableQuantity - requiredQuantity;
        StockMovement newStockMovement = new StockMovement();
        newStockMovement.setItem(stockMovement.getItem());
        newStockMovement.setQuantity(remainingQuantity);
        newStockMovement.setCreationDate(stockMovement.getCreationDate());
        stockMovementRepository.save(newStockMovement);
        
        requiredQuantity = 0;
      }
      stockMovement.setQuantity(stockMovement.getQuantity() - remainingQuantity);
      stockMovement.setOrder(order);
      stockMovementRepository.save(stockMovement);
    }

    // if order is fulfilled, set isCompleted to true
    if (requiredQuantity == 0) {
      order.setCompleted(true);
    }

    Order savedOrder = super.save(order);
    return savedOrder;
  }

}