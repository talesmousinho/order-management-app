package com.management.order.entities;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@Entity
@Data
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private OffsetDateTime creationDate;

  @NotNull
  @ManyToOne
  @JoinColumn(nullable = false)
  private Item item;

  @NotNull
  @Column(nullable = false)
  private int quantity;

  @NotNull
  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;

  private boolean isCompleted = false;

  @JsonManagedReference
  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
  private List<StockMovement> stockMovements;

  public Order(Long id, OffsetDateTime creationDate, Item item, int quantity, User user) {
    this.id = id;
    this.creationDate = creationDate;
    this.item = item;
    this.quantity = quantity;
    this.user = user;
  }

}
