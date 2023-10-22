package com.project.notifily.service;

import com.project.notifily.model.Checkpoint;
import com.project.notifily.repository.CheckpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckpointService {

    private final CheckpointRepository checkpointRepository;

    public CheckpointService(CheckpointRepository checkpointRepository){
        this.checkpointRepository = checkpointRepository;
    }

    public List<Checkpoint> findAll(){
       return checkpointRepository.findAll();
    }
}
