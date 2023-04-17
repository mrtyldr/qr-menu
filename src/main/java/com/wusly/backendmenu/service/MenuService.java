package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.category.Category;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.domain.notification.NotificationType;
import com.wusly.backendmenu.domain.restaurant.Menu;
import com.wusly.backendmenu.service.item.ItemService;
import com.wusly.backendmenu.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final NotificationService notificationService;

    public Menu getMenu(UUID restaurantId) {
        var categories = categoryService.getRestaurantCategories(restaurantId);
        return new Menu(mapTo(categories));
    }

    public Menu getMenu(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        var categories = categoryService.getRestaurantCategories(restaurant.getId());
        return new Menu(mapTo(categories));
    }

    private Map<String, List<ItemDto>> mapTo(List<Category> categories) {
        Map<String, List<ItemDto>> map = new HashMap<>();
        categories
                .forEach(c -> {
                    map.put(c.getName(), itemService.getRestaurantItemsDto(c.getRestaurantId(), c.getId()));
                });
        return map;
    }

    public List<ItemDto> getItems(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        return itemService.getRestaurantItemsDto(restaurant.getId());
    }

    public List<ItemDto> getItems(UUID restaurantId) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        return itemService.getRestaurantItemsDto(restaurant.getId());
    }

    public void callWaiter(UUID restaurantId, UUID tableId) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        notificationService.sendNotification(restaurant.getId(), NotificationType.WAITER, tableId);
    }

    public void callCheck(UUID restaurantId, UUID tableId) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        notificationService.sendNotification(restaurant.getId(), NotificationType.CHECK, tableId);
    }
}
