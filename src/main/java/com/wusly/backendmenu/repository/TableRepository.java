package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
    @Query("""
            select t.name
            from Table t
            where t.restaurantId = :restaurantId
            """)
    List<String> getTableNames(UUID restaurantId);
}
