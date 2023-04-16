package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.check.Check;
import com.wusly.backendmenu.domain.check.CheckStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CheckRepository extends JpaRepository<Check, UUID> {
    boolean existsByTableIdAndStatus(UUID tableId, CheckStatus checkStatus);

    Optional<Check> findByTableIdAndStatus(UUID tableId, CheckStatus checkStatus);
}
