package com.bsquare.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bsquare.dto.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notification")
public class Notification {
	private long id;
	private long userId;
	private String message;
	private String action;
	private String route;
	private NotificationStatus status;
	private  LocalDateTime timestamp;
	public Notification toEntity() {
		return
				new Notification(this.id,this.userId,this.message,this.action,this.route,this.status,this.timestamp);
	}
}
