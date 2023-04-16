package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.category.Category;
import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.domain.restaurant.Menu;
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
}
