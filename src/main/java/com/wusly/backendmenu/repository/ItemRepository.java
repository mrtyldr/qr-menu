package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
