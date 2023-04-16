package com.wusly.backendmenu.domain.order;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        String notes,
        List<UUID> itemIds,
        UUID tableId
) {
}
