package com.wusly.backendmenu.domain.item;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

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
        this.status = ItemStatus.IN_STOCK;
    }

    public Item() {
    }

    public void updated(String name, String description, BigDecimal price, UUID categoryId, String photoUrlLink) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.photoLinkUrl = photoUrlLink;
    }

    public void delete() {
        this.status = ItemStatus.DELETED;
    }
}
