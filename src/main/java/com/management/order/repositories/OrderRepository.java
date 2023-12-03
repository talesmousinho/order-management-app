package com.management.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.order.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
