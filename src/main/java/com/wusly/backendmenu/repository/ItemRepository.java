package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.item.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findAllByRestaurantId(UUID restaurantId);

    @Query("""
            select new com.wusly.backendmenu.domain.item.ItemDto(
            i.id,
            i.description,
            i.price,
            i.categoryId,
            c.name,
            i.photoLinkUrl
            )
            from Item i 
            inner join Category c on i.categoryId = c.id
            where i.restaurantId = :restaurantId and c.id = :categoryId
            """)
    List<ItemDto> getItemDtos(UUID restaurantId, UUID categoryId);

    @Query(
            """
                    select i from Item i
                    where i.id in :uuids and i.restaurantId = :restaurantId
                    """
    )
    List<Item> getOrderItems(List<UUID> uuids, UUID restaurantId);
}
