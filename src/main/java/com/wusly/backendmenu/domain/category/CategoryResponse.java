package com.wusly.backendmenu.domain.category;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
