package com.project.notifily.repository.impl;

import com.project.notifily.model.Notification;
import com.project.notifily.repository.NotificationRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final EntityManager em;

    public NotificationRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    public Page<Notification> findAllFiltered(Long status, String product, String dateStart, String dateEnd, Pageable pageable){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Notification> notificationRoot = criteriaQuery.from(Notification.class);
        notificationRoot.alias("entitySub");
        if (status != null){
            Predicate statusPredicate = criteriaBuilder.equal(notificationRoot.get("status").get("id"), status);
            predicates.add(statusPredicate);
        }
        if (product != null && !product.equals("")){
            Predicate productPredicate = criteriaBuilder.like(notificationRoot.join("products").get("name"), "%" + product + "%");
            predicates.add(productPredicate);
        }
        if (dateStart != null && dateEnd != null && !dateStart.equals("") && !dateEnd.equals("")){
            Predicate productPredicate = criteriaBuilder.between(notificationRoot.get("date_entrance"), dateStart, dateEnd);
            predicates.add(productPredicate);
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        List<Notification> result = em.createQuery(criteriaQuery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        List<Notification> allResult = em.createQuery(criteriaQuery).getResultList();
        Long count = Long.valueOf(allResult.size());
        Page<Notification> result1 = new PageImpl<>(result, pageable, count);
        return result1;
//        TypedQuery<Notification> query = em.createQuery(criteriaQuery);
//        return query.getResultList();
    }

}

