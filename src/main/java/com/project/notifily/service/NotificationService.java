package com.project.notifily.service;

import com.project.notifily.model.Notification;
import com.project.notifily.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public boolean existsById(Long id){ //TODO Возможно удалить
        return notificationRepository.existsById(id);
    }
    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }
    public void delete(Long id){
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

    public Notification newEmpty(Notification notification){ //TODO Возможно удалить
        if (notification == null || !(existsById(notification.getId()))){
            Notification notification1 = new Notification();
            save(notification1);
            return notification1;
        }
        else {
            return notification;
        }
    }
}
