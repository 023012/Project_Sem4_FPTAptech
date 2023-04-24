package com.library.service.impl;

import com.library.entity.Notification;
import com.library.repository.NotificationRepository;
import com.library.service.NotificationService;

import java.util.Calendar;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        Calendar cal = Calendar.getInstance();
        notification.setCreatedAt(cal.getTime());
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getAllNotificationById(Long userId) {
        return notificationRepository.getAllNotificationByUserID(userId);
    }

    @Override
    public String deleteNotification(Long id) {
        Notification notification = new Notification();
        if (notification == null){
            return "Cannot find Notification" + id;
        }else {
            notificationRepository.delete(notification);
            return "Notification "+id+ " has been delete !";
        }

    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {
        Notification notificationExisted =notificationRepository.findById(id).get();
        notificationExisted.setContent(notification.getContent());
        notificationRepository.save(notification);
        return notificationExisted;
    }
}
