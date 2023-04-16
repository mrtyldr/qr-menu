package com.wusly.backendmenu.domain.restaurant;

public record LoginCommand(
        String email,
        String password
) {
}
