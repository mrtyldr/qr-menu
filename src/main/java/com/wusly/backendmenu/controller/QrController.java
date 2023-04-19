package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.service.QRService;
import com.wusly.backendmenu.service.RestaurantService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr")
public class QrController {

    private final QRService qrService;
    private final RestaurantService restaurantService;

    @GetMapping("")
    Response<String> getBarcode(Principal principal) {
        var restaurant = restaurantService.getRestaurantByEmail(principal.getName());
        return Response.of(qrService.createQrForRestaurant(restaurant.getId()));
    }
}
