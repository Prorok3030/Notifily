package com.project.notifily.controller;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.service.NotificationService;
import com.project.notifily.service.ProductService;
import com.project.notifily.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UnitService unitService;
    private final NotificationService notificationService;


    @Autowired
    ProductController(ProductService productService, UnitService unitService, NotificationService notificationService){
        this.productService = productService;
        this.unitService = unitService;
        this.notificationService =notificationService;
    }

    @GetMapping("/products/{notifyId}")
    public String findByNotification(@PathVariable("notifyId") Long notifyId, Model model){
        List<Product> products = productService.findByNotificationId(notifyId );
        model.addAttribute("notifyId", notifyId);
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/productAdd/{notifyId}")
    public String productAdd(@PathVariable("notifyId") Long notifyId, Product product, Model model){
        model.addAttribute("notifyId", notifyId);
        model.addAttribute("units", unitService.findAll());
        return "product-add";
    }

    @PostMapping("/productAdd/{notifyId}")
    public String productAddPost(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("notifyId", product.getNotification().getId());
            model.addAttribute("units", unitService.findAll());
            return "product-add";
        }

        productService.save(product);
        return "redirect:/products/" + product.getNotification().getId();
    }

    @GetMapping("/productEdit/{id}")
    public String productEdit(@PathVariable("id") Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("units", unitService.findAll());
        return "product-edit";
    }

    @PostMapping("/productEdit/{id}")
    public String productEditPost(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("units", unitService.findAll());
            return "product-edit";
        }
        productService.save(product);
        return "redirect:/products/" + product.getNotification().getId();
    }

    @GetMapping("/productDelete/{id}")
    public String productDelete(@PathVariable("id") Long id){
        productService.delete(id);
        return "redirect:/products";
    }

}
