package com.wusly.backendmenu.domain.check;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckItemResponse(
        UUID itemId,
        String name,
        Integer quantity,
        BigDecimal total
) {
}
