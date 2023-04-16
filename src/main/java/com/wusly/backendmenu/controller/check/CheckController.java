package com.wusly.backendmenu.controller.check;

import com.wusly.backendmenu.domain.check.CloseCheckCommand;
import com.wusly.backendmenu.service.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/v1/check")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CheckController {
    private final CheckService checkService;


    @PutMapping("/close")
    void closeCheck(CloseCheckCommand command, Principal principal){
            checkService.closeCheck(command,principal.getName());
    }
}
