package com.wusly.backendmenu.domain.table;

import java.util.UUID;

public record TableDto (
        UUID id,
        String name
) {
    public TableDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
