package com.project.notifily.controller;

import com.project.notifily.model.Notification;
import com.project.notifily.model.Product;
import com.project.notifily.service.CheckpointService;
import com.project.notifily.service.NotificationService;
import com.project.notifily.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String findAll(Model model){
        List<Notification> notifications = notificationService.findAll();
         model.addAttribute("notifications",notifications);
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
