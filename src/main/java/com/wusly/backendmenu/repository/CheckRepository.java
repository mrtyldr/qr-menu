package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CheckRepository extends JpaRepository<Check, UUID> {
    boolean existsByTableIdAndStatus(UUID tableId, CheckStatus checkStatus);

    @Query("""
            select c 
            from Check c 
            where c.tableId = :tableId and c.status = :checkStatus
            """)
    Optional<Check> findByTableIdAndStatus(UUID tableId, CheckStatus checkStatus);
}
