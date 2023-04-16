package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.table.Table;
import com.wusly.backendmenu.domain.table.TableDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
    @Query("""
            select new com.wusly.backendmenu.domain.table.TableDto(
            t.id,
            t.name
            )
            from Table t
            where t.restaurantId = :restaurantId
            """)
    List<TableDto> getTableNames(UUID restaurantId);
    @Query("""
                        select new com.wusly.backendmenu.domain.table.TableDto(
                        t.id,
                        t.name
                        )
                        from  Table t
                        where t.id = :tableId
            """)
    Optional<TableDto> getTableDto(UUID tableId);

    List<Table> findAllByRestaurantId(UUID id);

    Optional<Table> findByIdAndRestaurantId(UUID id, UUID restaurantId);
}
