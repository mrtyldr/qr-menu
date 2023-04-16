package com.wusly.backendmenu.controller.menu;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.restaurant.Menu;
import com.wusly.backendmenu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/menu")
@CrossOrigin
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{restaurantId}")
    Response<Menu> getMenu(@PathVariable UUID restaurantId){
       return Response.of(menuService.getMenu(restaurantId)) ;
    }

}
