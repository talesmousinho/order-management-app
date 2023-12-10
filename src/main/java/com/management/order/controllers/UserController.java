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

import com.management.order.entities.User;
import com.management.order.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private static final Logger logger = LogManager.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    try {
      List<User> users = userService.findAll();
      if (users.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(users);
    } catch (Exception e) {
      logger.error("Error ocurred while trying to find all users: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    try {
      User user = userService.findById(id);
      return ResponseEntity.ok(user);

    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to find an user: " + e.getMessage());
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      logger.error("Error ocurred while trying to find an user: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody User user) {
    try {
      user.setId(null);
      return ResponseEntity.ok(userService.save(user));
    } catch (Exception e) {
      logger.error("Error ocurred while trying to save a new user: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
    try {
      // check if user exists
      userService.findById(id);
      // update user
      user.setId(id);
      return ResponseEntity.ok(userService.save(user));
      
    } catch (EntityNotFoundException e) {
      logger.error("Error ocurred while trying to update an user: " + e.getMessage());
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      logger.error("Error ocurred while trying to update an user: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    try {
      userService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error("Error ocurred while trying to delete an user: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
