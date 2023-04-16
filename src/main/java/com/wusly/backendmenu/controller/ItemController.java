package com.wusly.backendmenu.controller;

import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.UpdateItemCommand;
import com.wusly.backendmenu.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addItem(@RequestBody AddItemCommand command, @RequestParam(required = false) MultipartFile photo, Principal principal) {
        itemService.addItem(command, photo, principal.getName());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateItem(@RequestBody UpdateItemCommand command, @PathVariable UUID id, Principal principal) {
        itemService.updateItem(command, id, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@PathVariable UUID id, Principal principal) {
        itemService.delete(id,principal.getName());
    }

}
