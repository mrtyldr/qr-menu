package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.order.CreateOrderCommand;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceHelper {
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrder(UUID restaurantId, CreateOrderCommand command, BigDecimal total){
        Order order = new Order(
                restaurantId,
                UUID.randomUUID(),
                command.itemIds(),
                command.notes(),
                total,
                command.tableId(),
                OrderStatus.ACTIVE
        );
        orderRepository.save(order);
    }

    public boolean existsByTableIdAndStatus(UUID tableId, OrderStatus status) {
        return orderRepository.existsByTableIdAndStatus(tableId,status);
    }
}
