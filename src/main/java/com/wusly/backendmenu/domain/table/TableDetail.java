package com.wusly.backendmenu.domain.table;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckResponse;

import java.util.List;
import java.util.UUID;

public record TableDetail(
        UUID id,
        CheckResponse check,
        List<String> notes
) {
}
