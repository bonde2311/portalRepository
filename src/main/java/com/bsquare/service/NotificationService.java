package com.bsquare.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bsquare.dto.NotificationDTO;
import com.bsquare.dto.ResponseDTO;
import com.bsquare.entity.Notification;
import com.bsquare.exception.JobPortalException;

public interface NotificationService {

	public void sendNotification(NotificationDTO notificationDTO) throws JobPortalException;
	public List<Notification> getUnreadNotifications(Long userId);
	public void readNotification(Long id) throws JobPortalException;
}
