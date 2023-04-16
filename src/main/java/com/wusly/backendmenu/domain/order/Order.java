package com.wusly.backendmenu.domain.order;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Aggregate<UUID> {
        private UUID restaurantId;
        private List<UUID> itemIds;
        private String note;
        private BigDecimal total;
        private UUID tableId;


}
