package com.wusly.backendmenu.domain.notification;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Notification extends Aggregate<UUID> {
    private UUID restaurantId;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private UUID tableId;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    public Notification(UUID id,UUID restaurantId, NotificationType type, UUID tableId, NotificationStatus status) {
        this.id =id;
        this.restaurantId = restaurantId;
        this.type = type;
        this.tableId = tableId;
        this.status = status;
    }

    public void markAsRead() {
        this.status = NotificationStatus.READ;
    }
}
