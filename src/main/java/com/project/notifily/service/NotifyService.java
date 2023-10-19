package com.project.notifily.service;

import com.project.notifily.model.Notify;
import com.project.notifily.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService {

    private final NotifyRepository notifyRepository;

    @Autowired
    public NotifyService(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    public List<Notify> findAll(){
        return notifyRepository.findAll();
    }
}
