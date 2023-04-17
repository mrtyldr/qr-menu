package com.wusly.backendmenu.service.item;

import com.wusly.backendmenu.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceHelper {

    private final ItemRepository itemRepository;

    public boolean existsByCategoryId(UUID categoryId) {
        return itemRepository.existsByCategoryId(categoryId);
    }
}
