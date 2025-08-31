package com.bsquare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsquare.dto.NotificationDTO;
import com.bsquare.dto.NotificationStatus;
import com.bsquare.entity.Notification;
import com.bsquare.exception.JobPortalException;
import com.bsquare.repository.NotificationRepository;
import com.bsquare.utility.Utilities;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public void sendNotification(NotificationDTO notificationDTO) throws JobPortalException {
		notificationDTO.setId(Utilities.getNextSequence("notification"));
		notificationRepository.save(notificationDTO.toEntity());
		notificationDTO.setStatus(NotificationStatus.UNREAD);
		notificationDTO.setTimestamp(LocalDateTime.now());
	}

	@Override
	public List<Notification> getUnreadNotifications(Long userId) {
		return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
		
	}

	@Override
	public void readNotification(Long id) throws JobPortalException {
		Notification noti = notificationRepository.findById(id).orElseThrow(()->new JobPortalException("No Notification Found"));
		noti.setStatus(NotificationStatus.READ);
		notificationRepository.save(noti);
		
	}
	
}
