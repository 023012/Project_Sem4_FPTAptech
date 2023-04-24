package com.library.service;

import com.library.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    List<Notification> getAllNotification();
    List<Notification> getAllNotificationById(Long userId);
    String deleteNotification(Long id);
    Notification updateNotification(Long id, Notification notification);
}
