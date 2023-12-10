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

import com.management.order.entities.StockMovement;
import com.management.order.services.StockMovementService;

@RestController
@RequestMapping("/api/v1/stock-movements")
public class StockMovementController {

  private static final Logger logger = LogManager.getLogger(StockMovementController.class);

  @Autowired
  private StockMovementService stockMovementService;

  @GetMapping
  public ResponseEntity<List<StockMovement>> findAll() {
    try {
      List<StockMovement> stockMovements = stockMovementService.findAll();
      if (stockMovements.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(stockMovements);
    } catch (Exception e) {
      logger.error("Error ocurred while trying to find all stock movements: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<StockMovement> findById(@PathVariable Long id) {
    try {
      StockMovement stockMovement = stockMovementService.findById(id);
      return ResponseEntity.ok(stockMovement);

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to find a stock movement: " + e.getMessage());
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      logger.error("Error ocurred while trying to find a stock movement: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<StockMovement> create(@RequestBody StockMovement stockMovement) {
    try {
      if (stockMovement.getOrder() != null) {
        return ResponseEntity.badRequest().build();
      }
      
      stockMovement.setId(null);
      return ResponseEntity.ok(stockMovementService.save(stockMovement));
    } catch (Exception e) {
      logger.error("Error ocurred while trying to create a stock movement: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<StockMovement> update(@PathVariable Long id, @RequestBody StockMovement stockMovement) {
    try {
      // check if the stock movement exists
      stockMovementService.findById(id);
      // update stock movement
      stockMovement.setId(id);
      return ResponseEntity.ok(stockMovementService.save(stockMovement));
    
    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to update a stock movement: " + e.getMessage());
      return ResponseEntity.notFound().build();
      
    } catch (Exception e) {
      logger.error("Error ocurred while trying to update a stock movement: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    try {
      stockMovementService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error("Error ocurred while trying to delete a stock movement: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
