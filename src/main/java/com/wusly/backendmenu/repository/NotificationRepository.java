package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
