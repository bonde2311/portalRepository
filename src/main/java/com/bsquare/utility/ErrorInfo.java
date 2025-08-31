package com.bsquare.utility;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo { // this class used for exception handling and throwing it throe errorMesaagse
							// and errorCode
	private String errorMessage;
	private Integer errorCode;
	private LocalDateTime timeStamp;
}
