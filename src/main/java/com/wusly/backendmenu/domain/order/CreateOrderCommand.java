package com.wusly.backendmenu.domain.order;

import java.util.Map;
import java.util.UUID;

public record CreateOrderCommand(
        String notes,
        Map<String, String> itemIds,
        UUID tableId
) {
}
