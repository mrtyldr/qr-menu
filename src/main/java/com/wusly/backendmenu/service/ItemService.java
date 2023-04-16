package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.domain.item.UpdateItemCommand;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
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

    @Transactional
    public void updateItem(UpdateItemCommand command, UUID id, String email) {
        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item Not found!"));
        var restaurant = restaurantService.getRestaurantByEmail(email);
        if (!item.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("You don't have access to this item");

        item.updated(command);
        itemRepository.save(item);
    }

    @Transactional
    public void delete(UUID id, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);

        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found!"));

        if (!item.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("Item not found!");

        itemRepository.delete(item);
    }

    public List<Item> getRestaurantItems(UUID restaurantId) {
        return itemRepository.findAllByRestaurantId(restaurantId);

    }

    public List<ItemDto> getRestaurantItemsDto(UUID restaurantId, UUID categoryId) {
        return itemRepository.getItemDtos(restaurantId,categoryId);
    }

    public Set<Item> getOrderItems(Set<UUID> uuids, UUID restaurantId) {
        return itemRepository.getOrderItems(uuids,restaurantId);
    }

    public List<ItemDto> getRestaurantItemsDto(UUID restaurantId) {
        return itemRepository.getItemDtos(restaurantId);
    }
}
