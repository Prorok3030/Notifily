package com.project.notifily.service;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService( ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByNotificationId(Long id){
        return productRepository.findByNotificationId(id);
    }
    public Product save(Product product){
        return productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
