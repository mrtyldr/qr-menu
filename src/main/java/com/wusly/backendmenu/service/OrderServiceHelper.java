package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.order.CreateOrderCommand;
import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import com.wusly.backendmenu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceHelper {
    private final OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(UUID restaurantId, CreateOrderCommand command, BigDecimal total, Map<UUID,Integer> itemIds){
        Order order = new Order(
                UUID.randomUUID(),
                restaurantId,
                itemIds.keySet(),
                command.notes(),
                total,
                command.tableId(),
                OrderStatus.ACTIVE
        );
        return orderRepository.save(order);
    }

    public boolean existsByTableIdAndStatus(UUID tableId, OrderStatus status) {
        return orderRepository.existsByTableIdAndStatus(tableId,status);
    }

    public List<Order> findByTableIdAndStatus(UUID tableId, OrderStatus orderStatus) {
       return orderRepository.findAllByTableIdAndStatus(tableId,orderStatus);

    }

    public List<String> findNotesByTableIdAndStatus(UUID tableId, OrderStatus orderStatus) {
        return orderRepository.findAllNotesByTableIdAndStatus(tableId,orderStatus);
    }
}
