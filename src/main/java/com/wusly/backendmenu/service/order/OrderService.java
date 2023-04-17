package com.wusly.backendmenu.service.order;

import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.notification.NotificationType;
import com.wusly.backendmenu.domain.order.CreateOrderCommand;
import com.wusly.backendmenu.repository.OrderRepository;
import com.wusly.backendmenu.service.CheckService;
import com.wusly.backendmenu.service.item.ItemService;
import com.wusly.backendmenu.service.RestaurantService;
import com.wusly.backendmenu.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final ItemService itemService;
    private final OrderServiceHelper orderServiceHelper;
    private final NotificationService notificationService;
    private final CheckService checkService;


    public void createOrder(UUID restaurantId, CreateOrderCommand command, Map<UUID, Integer> itemIds) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        var items = itemService.getOrderItems(itemIds.keySet(), restaurantId);
        BigDecimal total = calculateTotal(items,itemIds);
        var order = orderServiceHelper.saveOrder(restaurant.getId(), command, total,itemIds);
        checkService.orderCreated(itemIds,command.tableId(),order);
        notificationService.sendNotification(restaurant.getId(), NotificationType.ORDER, command.tableId());
    }

    private BigDecimal calculateTotal(Set<Item> items, Map<UUID, Integer> itemMap) {
        BigDecimal total = BigDecimal.ZERO;
        for (var i : items) {
            total = total.add(i.getPrice().multiply(new BigDecimal(itemMap.get(i.getId()))));
        }
        return total;
    }


}
