package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantByEmail(String email){
        return restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }
}
