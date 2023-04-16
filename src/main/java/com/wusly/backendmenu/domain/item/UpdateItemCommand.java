package com.wusly.backendmenu.domain.item;

import java.math.BigDecimal;

public record UpdateItemCommand(
        String name,
        String description,
        BigDecimal price
) {
}
