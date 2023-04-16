package com.wusly.backendmenu.controller.table;

import com.wusly.backendmenu.domain.table.CreateTableCommand;
import com.wusly.backendmenu.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/table")
@CrossOrigin
public class TableController {
    private final TableService tableService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addTable(@RequestBody CreateTableCommand command, Principal principal){
        tableService.create(command,principal.getName());
    }
}
