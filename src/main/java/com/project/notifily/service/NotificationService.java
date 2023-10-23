package com.project.notifily.service;

import com.project.notifily.model.Notification;
import com.project.notifily.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
    public Notification findById(Long id){
        return notificationRepository.findById(id).orElse(null);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public String getDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public Notification formatDate(Notification notification){
        String dateOld = notification.getDate_entrance();
        LocalDate date1 = LocalDate.parse(dateOld, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateNew = date1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        notification.setDate_entrance(dateNew);
        return notification;
    }

    public Page<Notification> findPaginated(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
//        List<Notification> list;
//
//        if(notifications.size() < startItem){
//            list = Collections.emptyList();
//        }
//        else{
//            int toIndex = Math.min(startItem + pageSize, notifications.size());
//            list = notifications.subList(startItem, toIndex);
//        }

        Pageable pageable1 = PageRequest.of(currentPage,pageSize);
//        List<Notification> notifications1 = findAll();

        Page<Notification> notificationPage
                = notificationRepository.findAll(pageable1);

        return notificationPage;
    }
}
