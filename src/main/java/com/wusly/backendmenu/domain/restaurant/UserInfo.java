package com.wusly.backendmenu.domain.restaurant;

import java.util.UUID;

public record UserInfo(
        UUID id,
        String email,
        String name,
        String photoUrlLink
) {
    public UserInfo(UUID id, String email, String name, String photoUrlLink) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.photoUrlLink = photoUrlLink;
    }
}
