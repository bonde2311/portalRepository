package com.bsquare.dto;

import java.time.LocalDateTime;

import com.bsquare.entity.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

	private long id;
	private long userId;
	private String message;
	private String action;
	private String route;
	private NotificationStatus status;
	private LocalDateTime timestamp;
	
	public Notification toEntity() {
		return
				new Notification(this.id,this.userId,this.message,this.action,this.route,this.status,this.timestamp);
	}
}