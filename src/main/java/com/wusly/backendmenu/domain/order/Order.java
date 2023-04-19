package com.wusly.backendmenu.domain.order;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends Aggregate<UUID> {
    private UUID restaurantId;
    private Collection<UUID> itemIds;
    private String note;
    private BigDecimal total;
    private UUID tableId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(UUID id,
                 UUID restaurantId,
                 Collection<UUID> itemIds,
                 String note,
                 BigDecimal total,
                 UUID tableId,
                 OrderStatus status) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.itemIds = itemIds;
        this.note = note;
        this.total = total;
        this.tableId = tableId;
        this.status = status;
    }

    public void closed() {
        this.status = OrderStatus.CLOSED;
    }
}
