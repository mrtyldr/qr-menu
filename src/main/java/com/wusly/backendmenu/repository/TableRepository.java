package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
}
