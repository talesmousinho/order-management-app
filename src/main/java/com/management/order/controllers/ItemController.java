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

import com.management.order.entities.Item;
import com.management.order.services.ItemService;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

  private static final Logger logger = LogManager.getLogger(ItemController.class);

  @Autowired
  private ItemService itemService;

  @GetMapping
  public ResponseEntity<List<Item>> findAll() {
    try {
      List<Item> items = itemService.findAll();
      if (items.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(items);
    } catch (Exception e) {
      logger.error("Error ocurred while trying to find all items: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Item> findById(@PathVariable Long id) {
    try {
      Item item = itemService.findById(id);
      return ResponseEntity.ok(item);

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to find an item: " + e.getMessage());
      return ResponseEntity.notFound().build();
      
    } catch (Exception e) {
      logger.error("Error ocurred while trying to find an item: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<Item> create(@RequestBody Item item) {
    try {
      item.setId(null);
      return ResponseEntity.ok(itemService.save(item));
    } catch (Exception e) {
      logger.error("Error ocurred while trying to create an item: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
    try {
      // check if item exists
      itemService.findById(id);
      // update item
      item.setId(id);
      return ResponseEntity.ok(itemService.save(item));

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to update an item: " + e.getMessage());
      return ResponseEntity.notFound().build();
      
    } catch (Exception e) {
      logger.error("Error ocurred while trying to update an item: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    try {
      itemService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error("Error ocurred while trying to delete an item: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
