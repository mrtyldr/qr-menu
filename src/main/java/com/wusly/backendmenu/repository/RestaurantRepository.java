package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByEmail(String username);
}
