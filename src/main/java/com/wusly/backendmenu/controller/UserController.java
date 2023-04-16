package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.restaurant.AuthenticationResponse;
import com.wusly.backendmenu.domain.restaurant.LoginCommand;
import com.wusly.backendmenu.domain.restaurant.RestaurantCreateCommand;
import com.wusly.backendmenu.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(NO_CONTENT)
    void register(@RequestBody RestaurantCreateCommand command){
        authService.register(command);
    }

    @PostMapping("/login")
    Response<AuthenticationResponse> login(@RequestBody LoginCommand command){
        var response = authService.login(command);
        return Response.of(response);
    }
}
