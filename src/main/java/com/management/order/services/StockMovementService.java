package com.management.order.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.management.order.entities.Item;
import com.management.order.entities.Order;
import com.management.order.entities.StockMovement;
import com.management.order.repositories.OrderRepository;
import com.management.order.repositories.StockMovementRepository;

@Service
public class StockMovementService extends GenericCRUDService<StockMovement, Long> {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private EmailService emailService;

  private static final Logger logger = LogManager.getLogger(EmailService.class);

  public StockMovementService(StockMovementRepository stockMovementRepository) {
    super(stockMovementRepository);
  }

  @Override
  @Transactional
  public StockMovement save(StockMovement stockMovement) {
    Item requiredItem = stockMovement.getItem();
    int addedQuantity = stockMovement.getQuantity();

    // find all incomplete orders for the item
    List<Order> incompleteOrders = orderRepository.findIncompleteOrdersForItem(requiredItem);
   
    for (Order order : incompleteOrders) {
      // when stock is allocated, break the loop
      if (addedQuantity <= 0) {
        break;
      }

      int requiredQuantity = order.getQuantity() - order.getStockMovements().stream().mapToInt(StockMovement::getQuantity).sum();
      if (requiredQuantity <= addedQuantity) {
        // fulfill the order
        StockMovement newStockMovement = new StockMovement();
        newStockMovement.setItem(stockMovement.getItem());
        newStockMovement.setQuantity(requiredQuantity);
        newStockMovement.setOrder(order);
        super.save(newStockMovement);

        // update order status to completed and send email
        order.setCompleted(true);
        orderRepository.save(order);
        emailService.sendOrderCompletionEmail(order);
        logger.info("Order with ID {} is completed", order.getId());

        addedQuantity -= requiredQuantity;
      } else {
        // partially fulfill the order
        StockMovement newStockMovement = new StockMovement();
        newStockMovement.setItem(stockMovement.getItem());
        newStockMovement.setQuantity(addedQuantity);
        newStockMovement.setOrder(order);
        super.save(newStockMovement);

        addedQuantity = 0;
      }
    }

    if (addedQuantity > 0) {
      // If there's still stock left, save it as a standalone stock movement
      stockMovement.setQuantity(addedQuantity);
      super.save(stockMovement);
    }

    return stockMovement;
  }

}
