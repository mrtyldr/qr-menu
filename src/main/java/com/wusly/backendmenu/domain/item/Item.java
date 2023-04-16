package com.wusly.backendmenu.domain.item;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Item extends Aggregate<UUID> {
    private String name;

    private UUID restaurantId;

    private String description;

    private BigDecimal price;

    private UUID categoryId;

    private String photoLinkUrl;


    public Item(UUID id,
                String name,
                UUID restaurantId,
                String description,
                BigDecimal price,
                UUID categoryId,
                String photoLinkUrl) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.photoLinkUrl = photoLinkUrl;
    }

    public Item() {
    }

    public void updated(UpdateItemCommand command) {
        this.name = command.name();
        this.price = command.price();
        this.description = command.description();
    }
}
