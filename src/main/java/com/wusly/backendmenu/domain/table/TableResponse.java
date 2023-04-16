package com.wusly.backendmenu.domain.table;

import java.util.UUID;

public record TableResponse(
        UUID id,
        String name,
        TableStatus status
) {
}
