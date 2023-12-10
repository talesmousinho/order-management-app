package com.management.order.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.management.order.entities.Order;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  private static final Logger logger = LogManager.getLogger(EmailService.class);

  public void sendOrderCompletionEmail(Order order) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(order.getUser().getEmail());
    message.setSubject("Order Complete");
    message.setText("Your order with ID " + order.getId() + " is complete.");
    mailSender.send(message);

    logger.info("Email sent for order with ID: {}", order.getId());
  }
}

