package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.domain.restaurant.RestaurantSettings;
import com.wusly.backendmenu.domain.restaurant.UserInfo;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.RestaurantRepository;
import com.wusly.backendmenu.repository.RestaurantSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final PhotoUploadService photoUploadService;
    private final RestaurantSettingsRepository restaurantSettingsRepository;

    public Restaurant getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }

    public Restaurant getRestaurantById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant Not Found!!"));
    }

    public UserInfo getUserInfo(String email) {
        return restaurantRepository.getUserInfo(email);
    }

    @Transactional
    public String updatePhoto(MultipartFile photo, String email) {
        var restaurant = getRestaurantByEmail(email);
        var photoUrlLink = photoUploadService.uploadRestaurantPhoto(photo, restaurant);
        restaurant.photoUpdated(photoUrlLink);
        restaurantRepository.save(restaurant);
        return photoUrlLink;
    }

    @Transactional
    public void settings(MultipartFile photo, String firstUrl, String secondUrl, String email) {
        var restaurant = getRestaurantByEmail(email);
        var photoLink = photoUploadService.uploadRestaurantSettingPhoto(photo, restaurant);
        RestaurantSettings restaurantSettings = new RestaurantSettings(
                restaurant.getId(),
                firstUrl,
                secondUrl,
                photoLink
        );
        restaurantSettingsRepository.save(restaurantSettings);
    }

    public RestaurantSettings getSettings(String email) {
        var restaurant = getRestaurantByEmail(email);
        return restaurantSettingsRepository.findById(restaurant.getId())
                .orElseThrow(() -> new NotFoundException("Henüz bir setting oluşturmadınız!"));
    }
}
