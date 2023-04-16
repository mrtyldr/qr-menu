package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
