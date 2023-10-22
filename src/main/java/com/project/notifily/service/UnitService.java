package com.project.notifily.service;

import com.project.notifily.model.Unit;
import com.project.notifily.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository){
        this.unitRepository = unitRepository;
    }

    public List<Unit> findAll(){
       return unitRepository.findAll();
    }
}
