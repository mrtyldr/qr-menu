package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckStatus;
import com.wusly.backendmenu.domain.item.ItemDto;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.domain.table.*;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.CheckRepository;
import com.wusly.backendmenu.repository.ItemRepository;
import com.wusly.backendmenu.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {
    private final RestaurantService restaurantService;
    private final TableRepository tableRepository;
    private final OrderServiceHelper orderServiceHelper;
    private final ItemService itemService;
    private final CheckRepository checkRepository;

    @Transactional
    public void create(CreateTableCommand command, String email) {

        var restaurant = restaurantService.getRestaurantByEmail(email);

        var table = new Table(
                UUID.randomUUID(),
                command.name(),
                restaurant.getId()
        );

        tableRepository.save(table);
    }

    public List<TableDto> getTableNames(UUID restaurantId) {
        return tableRepository.getTableNames(restaurantId);
    }

    public TableDto getTableDto(UUID tableId) {
        return tableRepository.getTableDto(tableId)
                .orElseThrow(() -> new NotFoundException("Table not found!! id: %s".formatted(tableId)));
    }

    public List<TableResponse> getTableResponses(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);

        return tableRepository.findAllByRestaurantId(restaurant.getId())
                .stream()
                .map(this::toTableResponse)
                .toList();

    }

    private TableResponse toTableResponse(Table t) {
        var check = checkRepository.findByTableIdAndStatus(t.getId(), CheckStatus.ACTIVE)
                .orElse(new Check());
        return orderServiceHelper.existsByTableIdAndStatus(t.getId(), OrderStatus.ACTIVE) ? new TableResponse(
                t.getId(),
                t.getName(),
                TableStatus.IN_USE,
                check.getTotal()
        ) : new TableResponse(
                t.getId(),
                t.getName(),
                TableStatus.FREE,
                BigDecimal.ZERO
        );

    }

    public TableDetail getTableDetail(UUID tableId, String email) {
        var table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found!"));
        var restaurant = restaurantService.getRestaurantByEmail(email);
        if (!table.getRestaurantId().equals(restaurant.getId()))
            throw new NotFoundException("Table Not Found!");
        List<String> orderNotes = orderServiceHelper.findNotesByTableIdAndStatus(table.getId(), OrderStatus.ACTIVE);
        var check = checkRepository.findByTableIdAndStatus(tableId, CheckStatus.ACTIVE)
                .orElse(null);

        return new TableDetail(table.getId(), check, orderNotes);
    }

    @Transactional
    public void delete(UUID id, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        var table = tableRepository.findByIdAndRestaurantId(id, restaurant.getId())
                .orElseThrow(() -> new NotFoundException("table with id : %s not found!"));
        tableRepository.delete(table);
    }
}
