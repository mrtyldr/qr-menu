package com.wusly.backendmenu.service;

import com.wusly.backendmenu.domain.table.CreateTableCommand;
import com.wusly.backendmenu.domain.table.Table;
import com.wusly.backendmenu.domain.table.TableDto;
import com.wusly.backendmenu.error.NotFoundException;
import com.wusly.backendmenu.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {
    private final RestaurantService restaurantService;
    private final TableRepository tableRepository;

    @Transactional
    public void create(CreateTableCommand command, String email) {

        var restaurant = restaurantService.getRestaurantByEmail(email);

        var table = new Table(
                UUID.randomUUID(),
                command.name(),
                restaurant.getId()
        );

        tableRepository.save(table);
    }

    public List<TableDto> getTableNames(UUID restaurantId) {
        return tableRepository.getTableNames(restaurantId);
    }

    public TableDto getTableDto(UUID tableId) {
        return tableRepository.getTableDto(tableId)
                .orElseThrow(() -> new NotFoundException("Table not found!!"));
    }
}
