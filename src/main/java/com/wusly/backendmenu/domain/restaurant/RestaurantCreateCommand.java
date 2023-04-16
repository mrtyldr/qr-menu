package com.wusly.backendmenu.domain.restaurant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RestaurantCreateCommand(
        @Email
        String email,
        @NotNull
                @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        String password
) {
}
