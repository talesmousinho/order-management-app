package com.management.order.services;

import org.springframework.stereotype.Service;

import com.management.order.entities.StockMovement;
import com.management.order.repositories.StockMovementRepository;

@Service
public class StockMovementService extends GenericCRUDService<StockMovement, Long> {

  public StockMovementService(StockMovementRepository repository) {
    super(repository);
  }

}
