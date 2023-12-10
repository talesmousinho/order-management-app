package com.management.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.management.order.entities.Item;
import com.management.order.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT o FROM Order o WHERE o.item = :item AND o.isCompleted = false")
  List<Order> findIncompleteOrdersForItem(Item item);

}
