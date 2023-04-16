package com.wusly.backendmenu.domain.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDto(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        UUID categoryId,
        String categoryName,
        String photoLinkUrl
) {
    public ItemDto(UUID id, String name, String description, BigDecimal price, UUID categoryId, String categoryName, String photoLinkUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.photoLinkUrl = photoLinkUrl;
    }
}
