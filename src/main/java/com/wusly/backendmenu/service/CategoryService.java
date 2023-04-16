package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.category.AddCategoryCommand;
import com.wusly.backendmenu.domain.category.Category;
import com.wusly.backendmenu.domain.category.CategoryResponse;
import com.wusly.backendmenu.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;

    public boolean existsById(UUID id) {
        return categoryRepository.existsById(id);
    }

    @Transactional
    public void addCategory(AddCategoryCommand command, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);

        var category = new Category(
                UUID.randomUUID(),
                command.name(),
                restaurant.getId()
        );
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getCategories(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        return categoryRepository.findAllByRestaurantId(restaurant.getId())
                .stream().map(this::toResponse)
                .toList();
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(
                c.getId(),
                c.getName()
        );
    }

    public List<Category> getRestaurantCategories(UUID restaurantId) {
        return categoryRepository.findAllByRestaurantId(restaurantId);
    }
}