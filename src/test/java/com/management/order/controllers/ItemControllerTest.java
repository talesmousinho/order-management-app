package com.management.order.controllers;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.management.order.entities.Item;
import com.management.order.services.ItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private ItemService itemService;
  
  private List<Item> items;
  
  @BeforeEach
  public void setUp() {
    this.items = Arrays.asList(
    new Item(1L, "Item 1"),
    new Item(2L, "Item 2")
    );
  }
  
  @Test
  public void testFindAll() throws Exception {
    Mockito.when(itemService.findAll()).thenReturn(items);
    
    mockMvc.perform(get("/api/v1/items"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(2)))
    .andExpect(jsonPath("$[0].id", is(1)))
    .andExpect(jsonPath("$[0].name", is("Item 1")))
    .andExpect(jsonPath("$[1].id", is(2)))
    .andExpect(jsonPath("$[1].name", is("Item 2")));
  }
  
  @Test
  public void testFindById() throws Exception {
    Mockito.when(itemService.findById(1L)).thenReturn(items.get(0));
    
    mockMvc.perform(get("/api/v1/items/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(1)))
    .andExpect(jsonPath("$.name", is("Item 1")));
  }
  
  @Test
  public void testFindByIdNotFound() throws Exception {
    Mockito.when(itemService.findById(1L)).thenThrow(new EntityNotFoundException());
    
    mockMvc.perform(get("/api/v1/items/1"))
    .andExpect(status().isNotFound());
  }
  
  @Test
  public void testFindByIdBadRequest() throws Exception {
    Mockito.when(itemService.findById(1L)).thenThrow(new RuntimeException());
    
    mockMvc.perform(get("/api/v1/items/1"))
    .andExpect(status().isBadRequest());
  }
  
  
  @Test
  public void testCreate() throws Exception {
    Item item = new Item(null, "Item 1");
    Mockito.when(itemService.save(item)).thenReturn(items.get(0));
    
    mockMvc.perform(post("/api/v1/items")
    .contentType(MediaType.APPLICATION_JSON)
    .content(new ObjectMapper().writeValueAsString(item)))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(1)))
    .andExpect(jsonPath("$.name", is("Item 1")));
  }
  
  @Test
  public void testCreateBadRequest() throws Exception {
    Item item = new Item(null, "Item 1");
    Mockito.when(itemService.save(item)).thenThrow(new RuntimeException());
    
    mockMvc.perform(post("/api/v1/items")
    .contentType(MediaType.APPLICATION_JSON)
    .content(new ObjectMapper().writeValueAsString(item)))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void testUpdate() throws Exception {
    Item item = new Item(1L, "Item 1");
    Mockito.when(itemService.save(item)).thenReturn(item);
    
    mockMvc.perform(put("/api/v1/items/1")
    .contentType(MediaType.APPLICATION_JSON)
    .content(new ObjectMapper().writeValueAsString(item)))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(1)))
    .andExpect(jsonPath("$.name", is("Item 1")));
  }
  
  @Test
  public void testUpdateBadRequest() throws Exception {
    Item item = new Item(1L, "Item 1");
    Mockito.when(itemService.save(item)).thenThrow(new RuntimeException());
    
    mockMvc.perform(put("/api/v1/items/1")
    .contentType(MediaType.APPLICATION_JSON)
    .content(new ObjectMapper().writeValueAsString(item)))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void testUpdateNotFound() throws Exception {
    Item item = new Item(1L, "Item 1");
    Mockito.when(itemService.findById(1L)).thenThrow(new EntityNotFoundException());
    
    mockMvc.perform(put("/api/v1/items/1")
    .contentType(MediaType.APPLICATION_JSON)
    .content(new ObjectMapper().writeValueAsString(item)))
    .andExpect(status().isNotFound());
  }

  @Test
  public void testDelete() throws Exception {
    mockMvc.perform(delete("/api/v1/items/1"))
    .andExpect(status().isOk());
  }

  @Test
  public void testDeleteBadRequest() throws Exception {
    Mockito.doThrow(new RuntimeException()).when(itemService).deleteById(1L);
    
    mockMvc.perform(delete("/api/v1/items/1"))
    .andExpect(status().isBadRequest());
  }

}
