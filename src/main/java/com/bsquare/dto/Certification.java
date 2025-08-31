package com.bsquare.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
	
	private String cerName;
	private String issuer;
	private LocalDateTime issueDate;
	private String certificateId;
	

}
