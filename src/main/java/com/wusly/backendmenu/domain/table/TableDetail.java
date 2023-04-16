package com.wusly.backendmenu.domain.table;

import com.wusly.backendmenu.domain.check.Check;

import java.util.List;
import java.util.UUID;

public record TableDetail(
        UUID id,
        Check check,
        List<String> notes
) {
}
