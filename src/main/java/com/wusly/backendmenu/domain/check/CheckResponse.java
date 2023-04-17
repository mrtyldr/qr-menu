package com.wusly.backendmenu.domain.check;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CheckResponse (
        UUID id,
        List<CheckItemResponse> checkItems,
        BigDecimal total
){
}
