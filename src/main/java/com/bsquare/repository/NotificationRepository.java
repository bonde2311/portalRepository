package com.bsquare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bsquare.dto.NotificationStatus;
import com.bsquare.entity.Notification;

public interface NotificationRepository  extends MongoRepository<Notification, Long >{

	public List<Notification> findByUserIdAndStatus(Long userId,NotificationStatus status);
}
