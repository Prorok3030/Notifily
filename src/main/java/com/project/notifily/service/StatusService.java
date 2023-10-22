package com.project.notifily.service;

import com.project.notifily.model.Status;
import com.project.notifily.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    public List<Status> findAll(){
       return statusRepository.findAll();
    }
}
