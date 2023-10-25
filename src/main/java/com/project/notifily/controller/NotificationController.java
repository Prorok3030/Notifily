package com.project.notifily.controller;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.service.CheckpointService;
import com.project.notifily.service.NotificationService;
import com.project.notifily.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final StatusService statusService;
    private final CheckpointService checkpointService;

    @Autowired
    public NotificationController(NotificationService notificationService, StatusService statusService, CheckpointService checkpointService){
        this.notificationService = notificationService;
        this.statusService = statusService;
        this.checkpointService = checkpointService;
    }

    @GetMapping("/")
    public String findAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                          @RequestParam(required = false) Long status, @RequestParam(required = false) String product,
                          @RequestParam(required = false) String dateStart, @RequestParam(required = false) String dateEnd,
                          Model model, Notification notification){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Notification> notificationPage = notificationService.findPaginated(status, product, dateStart, dateEnd, page, size);
        model.addAttribute("notificationPage", notificationPage);
        int totalPages = notificationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
        }
        model.addAttribute("product",product);
        model.addAttribute("dateStart",dateStart);
        model.addAttribute("dateEnd",dateEnd);
        model.addAttribute("statusCur", status);
        model.addAttribute("statuses", statusService.findAll());
        return "notifications";
    }

    @GetMapping("/notificationAdd")
    public String NotificationAdd(Notification notification, Model model){
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("checkpoints", checkpointService.findAll());
        return "notification-add";
    }

    @PostMapping("/notificationAdd")
    public String NotificationAddPost(@ModelAttribute("notification") Notification notification){
//        notificationService.formatDate(notification);
        notification.setData_create(notificationService.getDate());
        notificationService.save(notification);
        return "redirect:/products/" + notification.getId();
    }

    @GetMapping("/notificationEdit/{id}")
    public String NotificationEdit(@PathVariable("id") Long id, Model model,
                                   @RequestHeader(value= "referer", required = false) final String referer){
        Notification notification = notificationService.findById(id);
        model.addAttribute("notification", notification);
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("checkpoints", checkpointService.findAll());
        if (referer != null){
            model.addAttribute("previousUrl", referer);
        }
        return "notification-edit";
    }

    @PostMapping("/notificationEdit")
    public String NotificationEditPost(Notification notification){
//        notificationService.formatDate(notification);
        notificationService.save(notification);
        return "redirect:/";
    }

    @GetMapping("/notificationDelete/{id}")
    public String NotificationDelete(@PathVariable("id") Long id){
        notificationService.delete(id);
        return "redirect:/";
    }
}
