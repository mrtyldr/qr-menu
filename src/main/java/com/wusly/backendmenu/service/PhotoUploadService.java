package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.restaurant.Restaurant;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


public interface PhotoUploadService {

    public String uploadItemPhoto(MultipartFile file, Restaurant restaurant, UUID itemId);

    public String uploadRestaurantPhoto(MultipartFile photo, Restaurant restaurant);

    public String updateItemPhoto(Item item, Restaurant restaurant, MultipartFile photo);

    public String uploadRestaurantSettingPhoto(MultipartFile photo, Restaurant restaurant);

    String uploadQrForRestaurant(UUID restaurantId, InputStream qrCode);
}
