package com.project.notifily.repository;

import com.project.notifily.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationRepositoryCustom {

    Page<Notification> findAllFiltered(Long status, String product, String dateStart, String DateEnd, Pageable pageable);
}
