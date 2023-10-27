package com.project.notifily.controller;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.service.NotificationService;
import com.project.notifily.service.ProductService;
import com.project.notifily.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String findByNotification(@PathVariable("notifyId") Long notifyId, @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size, Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Product> productsPage = productService.findPaginated(notifyId, currentPage, pageSize);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
        }
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("notifyId", notifyId);
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
        Product product = productService.findById(id);
        productService.delete(id);
        return "redirect:/products/" + product.getNotification().getId();
    }

}
