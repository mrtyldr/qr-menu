package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.restaurant.*;
import com.wusly.backendmenu.service.AuthService;
import com.wusly.backendmenu.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {

    private final AuthService authService;
    private final RestaurantService restaurantService;

    @PostMapping("/register")
    @ResponseStatus(NO_CONTENT)
    void register(@RequestBody RestaurantCreateCommand command) {
        authService.register(command);
    }

    @PostMapping("/login")
    Response<AuthenticationResponse> login(@RequestBody LoginCommand command) {
        var response = authService.login(command);
        return Response.of(response);
    }

    @GetMapping("/user-info")
    Response<UserInfo> userInfo(Principal principal) {
        return Response.of(restaurantService.getUserInfo(principal.getName()));
    }

    @PostMapping("/ping")
    void ping() {

    }

    @PutMapping(value = "/update-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<String> updatePhoto(@RequestParam MultipartFile photo, Principal principal) {
        return Response.of(restaurantService.updatePhoto(photo, principal.getName()));
    }

    @PostMapping(value = "/settings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(NO_CONTENT)
    void settings(@RequestParam(required = false) MultipartFile photo, @RequestParam(required = false) String firstUrl,
                  @RequestParam(required = false) String secondUrl, Principal principal) {
        restaurantService.settings(photo, firstUrl, secondUrl, principal.getName());
    }

    @GetMapping("/settings")
    Response<RestaurantSettings> getSettings(Principal principal) {
        return Response.of(restaurantService.getSettings(principal.getName()));
    }
}
