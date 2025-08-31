package com.bsquare.api;

import java.util.List;
import com.bsquare.service.NotificationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsquare.dto.ResponseDTO;
import com.bsquare.entity.Notification;
import com.bsquare.exception.JobPortalException;
import com.bsquare.service.NotificationService;


@RestController
@CrossOrigin
@RequestMapping("/notification")
@Validated
public class NotificationAPI {

    private final NotificationServiceImpl notificationService_1;
	
	@Autowired
	NotificationService notificationService;

    NotificationAPI(NotificationServiceImpl notificationService_1) {
        this.notificationService_1 = notificationService_1;
    }
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<Notification>>getNotifications(@PathVariable Long userId){
		return new ResponseEntity<>(notificationService.getUnreadNotifications(userId),HttpStatus.OK);
	}
	
	
	@PostMapping("/read/{id}")
	public ResponseEntity<ResponseDTO>readNotifications(@PathVariable Long id) throws JobPortalException{
		notificationService.readNotification(id);
		return new ResponseEntity<>(new ResponseDTO("Success"),HttpStatus.OK);

	
	}	
	
	
	
}






