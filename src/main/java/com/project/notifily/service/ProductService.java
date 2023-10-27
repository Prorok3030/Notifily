package com.project.notifily.service;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Page<Product> findPaginated(Long notifyId, Integer page, Integer size){

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> notificationPage
                = productRepository.findByNotificationId(notifyId, pageable);

        return notificationPage;
    }
}
