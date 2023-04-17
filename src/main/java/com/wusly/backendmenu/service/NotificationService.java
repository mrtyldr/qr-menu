package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.notification.Notification;
import com.wusly.backendmenu.domain.notification.NotificationResponse;
import com.wusly.backendmenu.domain.notification.NotificationStatus;
import com.wusly.backendmenu.domain.notification.NotificationType;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final RestaurantService restaurantService;
    private final TableService tableService;

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

    public List<NotificationResponse> getNotifications(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);

        return notificationRepository.getActiveNotificationsForRestaurant(restaurant.getId())
                .stream().map(this::mapToResponse)
                .toList();
    }

    private NotificationResponse mapToResponse(Notification n) {
        var tableDto = tableService.getTableDto(n.getTableId());
        return new NotificationResponse(
                n.getId(),
                n.getRestaurantId(),
                tableDto,
                n.getType(),
                n.getStatus()
        );
    }

    @Transactional
    public void markAsRead(UUID id, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification Not found!!"));
        if (!notification.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("Notification Not found!!");
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    public void tableDeleted(UUID id) {
        List<Notification> notifications = notificationRepository.findAllByTableId(id);
        notificationRepository.deleteAll(notifications);
    }
}
