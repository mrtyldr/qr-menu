package com.wusly.backendmenu.service.item;

import com.amazonaws.services.s3.AmazonS3;
import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.infrastructure.aws.S3Utils;
import com.wusly.backendmenu.repository.ItemRepository;
import com.wusly.backendmenu.service.CategoryService;
import com.wusly.backendmenu.service.PhotoUploadService;
import com.wusly.backendmenu.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
    private final AmazonS3 s3Client;

    @Transactional
    public void addItem(AddItemCommand command, MultipartFile photo, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        if (!categoryService.existsById(command.categoryId()))
            throw new NotFoundException("Category does not exists!");
        var itemId = UUID.randomUUID();
        Item item = new Item(
                itemId,
                command.name(),
                restaurant.getId(),
                command.description(),
                command.price(),
                command.categoryId(),
                photoUploadService.uploadItemPhoto(photo,restaurant,itemId)
        );
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(String name, String description, BigDecimal price, UUID categoryId, MultipartFile photo, UUID id, String email) {
        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item Not found!"));
        var restaurant = restaurantService.getRestaurantByEmail(email);
        if (!item.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("You don't have access to this item");
        var photoUrlLink = photoUploadService.updateItemPhoto(item, restaurant, photo);

        item.updated(name, description, price, categoryId, photoUrlLink);
        itemRepository.save(item);
    }

    @Transactional
    public void delete(UUID id, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);

        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item : %s not found!".formatted(id)));

        if (!item.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("Item : %s not found!".formatted(item.getId()));
        item.delete();
        itemRepository.save(item);
    }



    public List<ItemDto> getRestaurantItemsDto(UUID restaurantId, UUID categoryId) {
        return itemRepository.getItemDtos(restaurantId, categoryId)
                .stream().map(this::mapLinks).toList();
    }

    public Set<Item> getOrderItems(Set<UUID> uuids, UUID restaurantId) {
        return itemRepository.getOrderItems(uuids, restaurantId);
    }

    public List<ItemDto> getRestaurantItemsDto(UUID restaurantId) {
        return itemRepository.getItemDtos(restaurantId);
    }

    public Item findById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("item With id: %s not found!".formatted(itemId)));
    }

    private ItemDto mapLinks(ItemDto i) {
        if(i.photoLinkUrl() == null)
            return i;
        return new ItemDto(
                i.id(),
                i.name(),
                i.description(),
                i.price(),
                i.categoryId(),
                i.categoryName(),
                s3Client.generatePresignedUrl(S3Utils.getPublicUrlRequest(i.photoLinkUrl())).toString()
        );
    }
}
