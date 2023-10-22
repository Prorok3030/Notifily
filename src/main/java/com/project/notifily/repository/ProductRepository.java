package com.project.notifily.repository;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNotificationId (Long id);

}
