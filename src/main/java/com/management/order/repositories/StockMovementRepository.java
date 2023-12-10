package com.management.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.management.order.entities.Item;
import com.management.order.entities.StockMovement;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

  @Query("SELECT s FROM StockMovement s WHERE s.item = :item AND s.order IS NULL")
  List<StockMovement> findAvailableStockForItem(Item item);
  
}
