package com.wusly.backendmenu.controller.table;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.table.CreateTableCommand;
import com.wusly.backendmenu.domain.table.TableDetail;
import com.wusly.backendmenu.domain.table.TableResponse;
import com.wusly.backendmenu.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
@CrossOrigin
public class TableController {
    private final TableService tableService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addTable(@RequestBody CreateTableCommand command, Principal principal){
        tableService.create(command,principal.getName());
    }
    @GetMapping("")
    Response<List<TableResponse>> getTables(Principal principal){
        return Response.of(tableService.getTableResponses(principal.getName()));
    }

    @GetMapping("/{id}")
    Response<TableDetail> getTableDetail(@PathVariable UUID id, Principal principal){
        return Response.of(tableService.getTableDetail(id,principal.getName()));
    }
}
