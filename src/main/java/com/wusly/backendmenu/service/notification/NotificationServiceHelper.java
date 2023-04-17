package com.wusly.backendmenu.service.notification;

import com.wusly.backendmenu.domain.notification.Notification;
import com.wusly.backendmenu.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceHelper {

    private final NotificationRepository notificationRepository;

    public void tableDeleted(UUID id) {
        List<Notification> notifications = notificationRepository.findAllByTableId(id);
        notificationRepository.deleteAll(notifications);
    }

}
