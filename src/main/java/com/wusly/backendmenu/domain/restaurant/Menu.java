package com.wusly.backendmenu.domain.restaurant;

import com.wusly.backendmenu.domain.item.ItemDto;

import java.util.List;
import java.util.Map;

public record Menu (
        Map<String, List<ItemDto>> menu
){
}
