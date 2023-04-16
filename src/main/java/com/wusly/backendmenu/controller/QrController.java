package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.service.QRService;
import com.wusly.backendmenu.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.security.Principal;

@RestController("/api/v1/qr")
@CrossOrigin
@RequiredArgsConstructor
public class QrController {

    private final QRService qrService;
    private final RestaurantService restaurantService;

    @GetMapping(value = "/barcode", produces = MediaType.IMAGE_PNG_VALUE)
    BufferedImage getBarcode(Principal principal) {
        var restaurant = restaurantService.getRestaurantByEmail(principal.getName());
        return qrService.createQrForRestaurant(restaurant.getId());
    }
}
