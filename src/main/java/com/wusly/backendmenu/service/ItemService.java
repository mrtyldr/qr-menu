package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final RestaurantService restaurantService;
    private final PhotoUploadService photoUploadService;
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    @Transactional
    public void addItem(AddItemCommand command, MultipartFile photo, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        if (!categoryService.existsById(command.categoryId()))
            throw new NotFoundException("Category does not exists!");

        Item item = new Item(
                UUID.randomUUID(),
                command.name(),
                restaurant.getId(),
                command.description(),
                command.price(),
                command.categoryId(),
                photoUploadService.uploadPhoto(photo)
        );
        itemRepository.save(item);
    }
}
