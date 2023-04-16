package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.domain.restaurant.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByEmail(String username);

    @Query("""
            select new com.wusly.backendmenu.domain.restaurant.UserInfo(
            r.id,
            r.email,
            r.name,
            r.photoUrlLink
            )
            from Restaurant r
            where r.email = :email
            """)
    UserInfo getUserInfo(String email);
}
