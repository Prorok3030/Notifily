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
import java.util.Optional;

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

    public Page<Notification> findPaginated(Long status, String product, String dateStart, String dateEnd, Optional<Integer> page, Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage -1, pageSize);
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
                = notificationRepository.findAllFiltered(status,product,dateStart,dateEnd, pageable);

        return notificationPage;
    }
}
