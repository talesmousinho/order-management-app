package com.management.order.services;

import org.springframework.stereotype.Service;

import com.management.order.entities.Item;
import com.management.order.repositories.ItemRepository;

@Service
public class ItemService extends GenericCRUDService<Item, Long> {

  public ItemService(ItemRepository repository) {
    super(repository);
  }

}
