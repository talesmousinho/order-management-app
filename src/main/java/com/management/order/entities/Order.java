package com.management.order.entities;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

}
