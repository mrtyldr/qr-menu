package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.restaurant.RestaurantSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantSettingsRepository extends JpaRepository<RestaurantSettings, UUID> {
}
