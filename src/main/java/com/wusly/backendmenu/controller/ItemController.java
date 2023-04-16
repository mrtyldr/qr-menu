package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.UpdateItemCommand;
import com.wusly.backendmenu.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addItem(@RequestParam String name,
                 @RequestParam String description,
                 @RequestParam BigDecimal price,
                 @RequestParam UUID categoryId
            , @RequestParam(required = false) MultipartFile photo, Principal principal) {
        itemService.addItem(new AddItemCommand(name, description, price, categoryId), photo, principal.getName());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateItem(@RequestBody UpdateItemCommand command, @PathVariable UUID id, Principal principal) {
        itemService.updateItem(command, id, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@PathVariable UUID id, Principal principal) {
        itemService.delete(id, principal.getName());
    }

}
