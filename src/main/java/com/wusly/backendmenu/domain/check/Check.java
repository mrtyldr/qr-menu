package com.wusly.backendmenu.domain.check;

import com.wusly.backendmenu.core.Aggregate;
import com.wusly.backendmenu.domain.order.CheckItems;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "checks")
@Getter
public class Check extends Aggregate<UUID> {
    @ElementCollection(fetch = FetchType.LAZY)
    List<CheckItems> items;
    BigDecimal total;

    UUID tableId;
    @Enumerated(EnumType.STRING)
    CheckStatus status;

    public Check(UUID id, List<CheckItems> items, BigDecimal total, UUID tableId, CheckStatus status) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.tableId = tableId;
        this.status = status;
    }

    public Check() {
    }

    public void update(List<CheckItems> checkItems, BigDecimal total) {
        this.items.addAll(checkItems);
        this.total = total.add(this.total);
    }
}
