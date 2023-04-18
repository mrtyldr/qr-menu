package com.wusly.backendmenu.controller.item;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.item.AddItemCommand;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.domain.item.UpdateItemCommand;
import com.wusly.backendmenu.domain.restaurant.Menu;
import com.wusly.backendmenu.service.item.ItemService;
import com.wusly.backendmenu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {
    private final ItemService itemService;
    private final MenuService menuService;

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
    void updateItem(@RequestParam String name,
                    @RequestParam String description,
                    @RequestParam BigDecimal price,
                    @RequestParam UUID categoryId
            , @RequestParam(required = false) MultipartFile photo, @PathVariable UUID id, Principal principal) {
        itemService.updateItem(name, description, price, categoryId, photo, id, principal.getName());
    }

    @GetMapping("/menu")
    Response<Menu> getMenu(Principal principal) {
        return Response.of(menuService.getMenu(principal.getName()));
    }

    @GetMapping("")
    Response<List<ItemDto>> getItems(Principal principal) {
        return Response.of(menuService.getItems(principal.getName()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@PathVariable UUID id, Principal principal) {
        itemService.delete(id, principal.getName());
    }

}
