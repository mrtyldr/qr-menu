package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    @Query("""
            select n from Notification n
            where n.restaurantId = :restaurantId and n.status = 'SENT'
            order by n.createdAt desc
            """)
    List<Notification> getActiveNotificationsForRestaurant(UUID restaurantId);

    List<Notification> findAllByTableId(UUID tableId);

    List<Notification> findAllByRestaurantId(UUID restaurantId);
}
