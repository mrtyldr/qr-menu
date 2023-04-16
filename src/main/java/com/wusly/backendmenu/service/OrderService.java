package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.notification.NotificationType;
import com.wusly.backendmenu.domain.order.CreateOrderCommand;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final ItemService itemService;
    private final OrderServiceHelper orderServiceHelper;
    private final NotificationService notificationService;


    public void createOrder(UUID restaurantId, CreateOrderCommand command) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        var items = itemService.getOrderItems(command.itemIds(), restaurantId);
        BigDecimal total = calculateTotal(items);
        orderServiceHelper.saveOrder(restaurant.getId(), command, total);
        notificationService.sendNotification(restaurant.getId(), NotificationType.ORDER, command.tableId());
    }

    private BigDecimal calculateTotal(List<Item> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (var i : items) {
            total = total.add(i.getPrice());
        }
        return total;
    }
}
