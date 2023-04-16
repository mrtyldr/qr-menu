package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.order.Order;
import com.wusly.backendmenu.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByTableIdAndStatus(UUID tableId, OrderStatus status);

    List<Order> findAllByTableIdAndStatus(UUID tableId, OrderStatus orderStatus);
    @Query("""
            select o.note 
            from Order o
            where o.tableId = :tableId and o.status = :orderStatus
            """)
    List<String> findAllNotesByTableIdAndStatus(UUID tableId, OrderStatus orderStatus);
}
