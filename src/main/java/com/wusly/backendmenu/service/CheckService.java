package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckStatus;
import com.wusly.backendmenu.domain.check.CloseCheckCommand;
import com.wusly.backendmenu.domain.order.CheckItems;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.CheckRepository;
import com.wusly.backendmenu.service.order.OrderServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckService {
    private final CheckRepository checkRepository;
    private final RestaurantService restaurantService;
    private final OrderServiceHelper orderServiceHelper;


    public void orderCreated(Map<UUID, Integer> itemIds, UUID tableId, Order order) {
        var checkItems = getCheckItems(itemIds);
        if (!checkRepository.existsByTableIdAndStatus(tableId, CheckStatus.ACTIVE))
            newCheck(tableId, order, checkItems);
        else
            addCheck(tableId, order, checkItems, itemIds);
    }

    private void addCheck(UUID tableId, Order order, Set<CheckItems> checkItems, Map<UUID, Integer> itemIds) {
        var check = checkRepository.findByTableIdAndStatus(tableId, CheckStatus.ACTIVE)
                .orElseThrow();
        check.getItems().stream()
                .filter(checkItems::contains)
                .forEach(i -> i.setQuantity(itemIds.get(i.getItemId()) + i.getQuantity()));
        var itemsNotInCheck = checkItems.stream()
                .filter(i -> !check.getItems().contains(i))
                .collect(Collectors.toSet());
        check.update(itemsNotInCheck, order.getTotal());
        checkRepository.save(check);
    }

    private void newCheck(UUID tableId, Order order, Set<CheckItems> checkItems) {
        var check = new Check(
                UUID.randomUUID(),
                checkItems,
                order.getTotal(),
                tableId,
                CheckStatus.ACTIVE
        );
        checkRepository.save(check);
    }

    private Set<CheckItems> getCheckItems(Map<UUID, Integer> itemIds) {
        Set<CheckItems> checkItems = new HashSet<>();
        itemIds.forEach((uuid, integer) -> checkItems.add(new CheckItems(uuid, integer)));
        return checkItems;
    }

    @Transactional
    public void closeCheck(CloseCheckCommand command, String email) {
        var restaurant = restaurantService.getRestaurantByEmail(email);
        var check = checkRepository.findByTableIdAndStatus(command.tableId(), CheckStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("CheckNotFound"));
        var orders = orderServiceHelper.findByTableIdAndStatus(command.tableId(), OrderStatus.ACTIVE);
        orders.forEach(Order::closed);
        check.closed();
        checkRepository.save(check);
        orderServiceHelper.saveAllOrders(orders);
    }
}
