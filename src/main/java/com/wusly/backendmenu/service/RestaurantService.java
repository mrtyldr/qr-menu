package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantByEmail(String email){
        return restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }

    public Restaurant getRestaurantById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant Not Found!!"));
    }
}
