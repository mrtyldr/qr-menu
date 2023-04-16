package com.wusly.backendmenu.domain.notification;

import com.wusly.backendmenu.domain.table.TableDto;

import java.util.UUID;

public record NotificationResponse(
        UUID id,
        UUID restaurantId,
        TableDto tableDto,
        NotificationType type,
        NotificationStatus status
) {
}
