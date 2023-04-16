package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.notification.Notification;
import com.wusly.backendmenu.domain.notification.NotificationStatus;
import com.wusly.backendmenu.domain.notification.NotificationType;
import com.wusly.backendmenu.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(UUID restaurantId, NotificationType notificationType, UUID tableId) {
        Notification notification = new Notification(
                UUID.randomUUID(),
                restaurantId,
                notificationType,
                tableId,
                NotificationStatus.SENT
        );
        notificationRepository.save(notification);
    }
}
