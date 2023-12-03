package com.management.order.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericCRUDService<T, ID> {

  private final JpaRepository<T, ID> repository;

  protected GenericCRUDService(JpaRepository<T, ID> repository) {
    this.repository = repository;
  }

  public List<T> findAll() {
    return repository.findAll();
  }

  public T findById(ID id) {
    return repository.findById(id).orElse(null);
  }

  public T save(T entity) {
    return repository.save(entity);
  }

  public void deleteById(ID id) {
    repository.deleteById(id);
  }

}
