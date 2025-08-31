package com.bsquare.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsquare.dto.LoginDTO;
import com.bsquare.dto.ResponseDTO;
import com.bsquare.dto.UserDTO;
import com.bsquare.exception.JobPortalException;
import com.bsquare.repository.UserRepository;
import com.bsquare.service.UserService;
import com.bsquare.service.UserServiceImpl;
import com.mongodb.client.model.ReturnDocument;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@RestController // Marks this class as a Spring REST controller
@CrossOrigin // Allows Cross-Origin requests (frontend can call APIs without CORS issues)
@Validated // Enables validation on request payloads
@RequestMapping("/users") // Base URL for all endpoints inside this controller (e.g., /users/register)
public class UserAPI {

	private final UserServiceImpl userService_1; // Injecting UserServiceImpl (though not used directly)

	private final UserRepository userRepository; // Injecting UserRepository (though not used directly)

	@Autowired // Field injection of UserService (actual service used for method calls)
	private UserService userService;

	// Constructor for injecting dependencies
	UserAPI(UserRepository userRepository, UserServiceImpl userService_1) {
		this.userRepository = userRepository;
		this.userService_1 = userService_1;
	}

	// API to register a new user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws JobPortalException {
		// @Valid ensures UserDTO fields are validated
		userDTO = userService.registerUser(userDTO); // Calls service to handle registration logic
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED); // Returns 201 CREATED with registered user data
	}

	// API for user login
	@PostMapping("/login")
	public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
		// @Valid ensures LoginDTO fields are validated
		return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
		// Returns 200 OK with login response
	}

	// API for login forgot password
	@PostMapping("/changePass")
	public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
		return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
		// Returns 200 OK with login response
	}

	// API for user sendOTP Email
	@PostMapping("/sendOtp/{email}")
	public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email)
			throws Exception {
		userService.sendOtp(email);
		return new ResponseEntity<>(new ResponseDTO("OTP sent successfully."), HttpStatus.OK);
		// Returns 200 OK with sendOtp email response
	}

	// API for user login
	@GetMapping("/verifyOtp/{email}/{otp}")
	public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message = "{user.email.invalid}") String email,
			@PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}") String otp)
			throws JobPortalException {
		userService.verifyOtp(email, otp);
		return new ResponseEntity<>(new ResponseDTO("OTP has been verified."), HttpStatus.OK);
		// Returns 200 OK with sendOtp email response
	}

}
