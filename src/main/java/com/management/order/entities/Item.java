package com.management.order.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Set<Order> orders;

  @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Set<StockMovement> stockMovements;

}
