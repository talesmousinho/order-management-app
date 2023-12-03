package com.management.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.order.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
