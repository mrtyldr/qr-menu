package com.wusly.backendmenu.domain.check;

import java.util.UUID;

public record CloseCheckCommand(
        UUID tableId,
        UUID restaurantId
) {
}
