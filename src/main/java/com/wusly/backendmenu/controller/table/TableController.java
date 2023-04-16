package com.wusly.backendmenu.controller.table;

import com.wusly.backendmenu.domain.table.CreateTableCommand;
import com.wusly.backendmenu.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/table")
public class TableController {
    private final TableService tableService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addTable(CreateTableCommand command, Principal principal){
        tableService.create(command,principal.getName());
    }
}
