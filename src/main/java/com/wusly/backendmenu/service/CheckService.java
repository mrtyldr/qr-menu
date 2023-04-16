package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckStatus;
import com.wusly.backendmenu.domain.order.CheckItems;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckService {
    private final CheckRepository checkRepository;


    public void orderCreated(Map<UUID, Integer> itemIds, UUID tableId, Order order) {
        var checkItems = getCheckItems(itemIds);
        if (!checkRepository.existsByTableIdAndStatus(tableId, CheckStatus.ACTIVE))
            newCheck(tableId, order, checkItems);
        else
            addCheck(tableId, order, checkItems, itemIds);
    }

    private void addCheck(UUID tableId, Order order, List<CheckItems> checkItems, Map<UUID, Integer> itemIds) {
        var check = checkRepository.findByTableIdAndStatus(tableId, CheckStatus.ACTIVE)
                .orElseThrow();
        check.getItems().stream()
                .filter(checkItems::contains)
                .forEach(i -> i.setQuantity(itemIds.get(i.getItemId()) + i.getQuantity()));
        var itemsNotInCheck = check.getItems().stream()
                .filter(i -> !checkItems.contains(i))
                .toList();
        check.update(itemsNotInCheck, order.getTotal());
        checkRepository.save(check);
    }

    private void newCheck(UUID tableId, Order order, List<CheckItems> checkItems) {
        var check = new Check(
                UUID.randomUUID(),
                checkItems,
                order.getTotal(),
                tableId,
                CheckStatus.ACTIVE
        );
        checkRepository.save(check);
    }

    private List<CheckItems> getCheckItems(Map<UUID, Integer> itemIds) {
        List<CheckItems> checkItems = new ArrayList<>();
        itemIds.forEach((uuid, integer) -> checkItems.add(new CheckItems(uuid, integer)));
        return checkItems;
    }
}
