package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.domain.table.*;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {
    private final RestaurantService restaurantService;
    private final TableRepository tableRepository;
    private final OrderServiceHelper orderServiceHelper;

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
                .orElseThrow(() -> new NotFoundException("Table not found!!"));
    }

    public List<TableResponse> getTableResponses(String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        return tableRepository.findAllByRestaurantId(restaurant.getId())
                .stream()
                .map(t -> toTableResponse(t))
                .toList();

    }

    private TableResponse toTableResponse(Table t) {
        return orderServiceHelper.existsByTableIdAndStatus(t.getId(), OrderStatus.ACTIVE) ? new TableResponse(
                t.getId(),
                t.getName(),
                TableStatus.IN_USE
        ) : new TableResponse(
                t.getId(),
                t.getName(),
                TableStatus.FREE
        );

    }
}
