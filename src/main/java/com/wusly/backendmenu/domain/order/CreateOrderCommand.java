package com.wusly.backendmenu.domain.order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CreateOrderCommand(
        String notes,
        UUID tableId
) {
}