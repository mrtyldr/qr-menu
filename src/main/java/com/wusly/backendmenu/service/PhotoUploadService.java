package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.restaurant.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoUploadService {

    public String uploadPhoto(MultipartFile file){
        return "uploaded";
    }

    public String uploadRestaurantPhoto(MultipartFile photo, Restaurant restaurant) {
        return "uploaded";
    }

    public String uploadRestaurantSettingPhoto(MultipartFile photo, Restaurant restaurant) {
        return "uploaded";
    }
}
