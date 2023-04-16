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
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends Aggregate<UUID> {
    private UUID restaurantId;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private UUID tableId;

}
