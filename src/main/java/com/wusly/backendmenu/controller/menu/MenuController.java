package com.wusly.backendmenu.controller.menu;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.order.CreateOrderCommand;
import com.wusly.backendmenu.domain.restaurant.Menu;
import com.wusly.backendmenu.domain.table.TableDto;
import com.wusly.backendmenu.service.MenuService;
import com.wusly.backendmenu.service.OrderService;
import com.wusly.backendmenu.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/menu")
@RequiredArgsConstructor
@CrossOrigin
public class MenuController {

    private final MenuService menuService;
    private final TableService tableService;
    private final OrderService orderService;

    @GetMapping("/{restaurantId}")
    @CrossOrigin
    Response<Menu> getMenu(@PathVariable UUID restaurantId) {
        return Response.of(menuService.getMenu(restaurantId));
    }

    @GetMapping("/{restaurantId}/tables")
    @CrossOrigin
    Response<List<TableDto>> getTables(@PathVariable UUID restaurantId) {
        return Response.of(tableService.getTableNames(restaurantId));
    }

    @PostMapping("/{restaurantId}/order")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(value = "https://localhost:3000/**")
    void createOrder(@PathVariable UUID restaurantId,
                     @RequestBody CreateOrderCommand command) {
        Map<UUID, Integer> itemIdsUuid = new HashMap<>();
        command.itemIds().forEach((id, quantity) -> itemIdsUuid.put(UUID.fromString(id), Integer.parseInt(quantity)));
        orderService.createOrder(restaurantId, command, itemIdsUuid);
    }


}
