package com.wusly.backendmenu.domain.table;

import java.math.BigDecimal;
import java.util.UUID;

public record TableResponse(
        UUID id,
        String name,
        TableStatus status,
        BigDecimal total
) {
}
