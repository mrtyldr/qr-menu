package com.wusly.backendmenu.domain.item;

import java.math.BigDecimal;
import java.util.UUID;

public record AddItemCommand(
        String name,
        String description,
        BigDecimal price,
        UUID categoryId
) {
}
