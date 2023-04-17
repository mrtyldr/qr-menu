package com.wusly.backendmenu.controller.notification;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.notification.NotificationResponse;
import com.wusly.backendmenu.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("")
    Response<List<NotificationResponse>> getNotifications(Principal principal) {
        return Response.of(notificationService.getNotifications(principal.getName()));
    }
    @PutMapping("/{id}/mark-as-read")
    void markAsRead(@PathVariable UUID id, Principal principal){
        notificationService.markAsRead(id,principal.getName());
    }
}
