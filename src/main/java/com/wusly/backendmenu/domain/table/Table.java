package com.wusly.backendmenu.domain.table;

import com.wusly.backendmenu.core.Aggregate;

import jakarta.persistence.Entity;
import lombok.Setter;


import java.util.UUID;
@Entity
@Setter
@jakarta.persistence.Table(name = "tables")
public class Table extends Aggregate<UUID> {

    private String name;

    private UUID restaurantId;

    public Table(UUID id,String name, UUID restaurantId) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
    }

    public Table() {
    }

    public String getName() {
        return name;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }
}
