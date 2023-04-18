package com.wusly.backendmenu.service;



import com.wusly.backendmenu.domain.restaurant.AuthenticationResponse;
import com.wusly.backendmenu.domain.restaurant.LoginCommand;
import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.domain.restaurant.RestaurantCreateCommand;
import com.wusly.backendmenu.error.WrongUserNamePasswordException;
import com.wusly.backendmenu.repository.RestaurantRepository;
import com.wusly.backendmenu.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    @Transactional
    public void register(RestaurantCreateCommand command){
        var restaurant = new Restaurant(
                UUID.randomUUID(),
                command.email(),
                command.name(),
                null,
                passwordEncoder.encode(command.password())
        );
        restaurantRepository.save(restaurant);
    }

    public AuthenticationResponse login(LoginCommand command){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.email(),
                        command.password()
                )
        );
        var user = restaurantRepository.findByEmail(command.email())
                .orElseThrow(() -> new WrongUserNamePasswordException("Authentication is not successful!!"));


        return new AuthenticationResponse(
                jwtService.generateToken(user)
        );
    }
}
