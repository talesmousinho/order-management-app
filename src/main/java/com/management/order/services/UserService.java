package com.management.order.services;

import org.springframework.stereotype.Service;

import com.management.order.entities.User;
import com.management.order.repositories.UserRepository;

@Service
public class UserService extends GenericCRUDService<User, Long> {

  public UserService(UserRepository repository) {
    super(repository);
  }

}
