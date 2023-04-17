package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckItemResponse;
import com.wusly.backendmenu.domain.check.CheckResponse;
import com.wusly.backendmenu.domain.check.CheckStatus;
import com.wusly.backendmenu.domain.order.CheckItems;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.domain.table.*;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.CheckRepository;
import com.wusly.backendmenu.repository.TableRepository;
import com.wusly.backendmenu.service.item.ItemService;
import com.wusly.backendmenu.service.notification.NotificationServiceHelper;
import com.wusly.backendmenu.service.order.OrderServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final NotificationServiceHelper notificationServiceHelper;

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
        if (checkRepository.existsByTableIdAndStatus(table.getId(), CheckStatus.ACTIVE)) {
            CheckResponse checkResponse = getCheckResponse(tableId);
        }

        return new TableDetail(table.getId(), null, null);
    }

    private CheckResponse getCheckResponse(UUID tableId) {
        var check = checkRepository.findByTableIdAndStatus(tableId, CheckStatus.ACTIVE)
                .orElseThrow();
        var checkItemResponses = check.getItems().stream()
                .map(this::toCheckItemResponse)
                .toList();
        CheckResponse checkResponse = new CheckResponse(
                check.getId(),
                checkItemResponses,
                check.getTotal()
        );
        return checkResponse;
    }

    private CheckItemResponse toCheckItemResponse(CheckItems i) {
        var item = itemService.findById(i.getItemId());
        return new CheckItemResponse(item.getId(), item.getName(), i.getQuantity(), item.getPrice().multiply(new BigDecimal(i.getQuantity())));
    }

    @Transactional
    public void delete(UUID id, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        notificationServiceHelper.tableDeleted(id);
        var table = tableRepository.findByIdAndRestaurantId(id, restaurant.getId())
                .orElseThrow(() -> new NotFoundException("table with id : %s not found!"));
        tableRepository.delete(table);
    }
}
