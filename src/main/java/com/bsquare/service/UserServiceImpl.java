package com.bsquare.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bsquare.dto.LoginDTO;
import com.bsquare.dto.NotificationDTO;
import com.bsquare.dto.NotificationStatus;
import com.bsquare.dto.ResponseDTO;
import com.bsquare.dto.UserDTO;
import com.bsquare.entity.Data;
import com.bsquare.entity.OTP;
import com.bsquare.entity.User;
import com.bsquare.exception.JobPortalException;
import com.bsquare.repository.NotificationRepository;
import com.bsquare.repository.OTPRepository;
import com.bsquare.repository.UserRepository;
import com.bsquare.utility.Utilities;

import jakarta.mail.internet.MimeMessage;

@Service(value = "userService") // Marks this class as a Spring service with bean name "userService"
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository; // Injecting UserRepository to interact with database

	@Autowired
	private PasswordEncoder passwordEncoder; // Injecting PasswordEncoder to hash user passwords

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	NotificationService notificationService;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {

		// Check if user with the given email already exists
		Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
		if (optional.isPresent())
			throw new JobPortalException("USER_FOUND");
		
		// 
		userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));

		// Generate next sequence ID for new user
		userDTO.setId(Utilities.getNextSequence("users"));

		// Encode the plain password using BCrypt hashing
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		// Convert UserDTO to User entity
		User user = userDTO.toEntity();

		// Save the new user entity into the database
		user = userRepository.save(user);

		// Convert saved User entity back to DTO and return
		return user.toDTO();
	}

	@Override
	public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {

		// Fetch the user by email; if not found, throw an exception
		User user = userRepository.findByEmail(loginDTO.getEmail())
				.orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

		// Verify if the provided password matches the stored hashed password
		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
			throw new JobPortalException("INVALID_CREDENTIALS");

		// If authentication is successful, return the user as DTO
		return user.toDTO();
	}

	// Mail Send OTP Method
	@Override
	public Boolean sendOtp(String email) throws Exception {
		// Fetch the user by email; if not found, throw an exception
		User user = userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mm, true);
		message.setTo(email);
		message.setSubject("Your OTP Code");
		String genOTP = Utilities.generateOTP();
		OTP otp = new OTP(email, genOTP, LocalDateTime.now());
		otpRepository.save(otp);
		message.setText(Data.getMessageBodyString(genOTP, user.getFirstName() + " " + user.getLastName()), true);
		mailSender.send(mm);
		return true;

	}

	// verifyOTP method for Email
	@Override
	public Boolean verifyOtp(String email, String otp) throws JobPortalException {
		OTP otpEntity = otpRepository.findById(email).orElseThrow(() -> new JobPortalException("OTP_NOT_FOUND"));
		if (!otpEntity.getOtpCode().equals(otp))
			throw new JobPortalException("OTP_INCORRECT");
		return true;
	}

	// changePassword method for forgot pass in Login
	@Override
	public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		userRepository.save(user);
		NotificationDTO noti = new NotificationDTO();
		noti.setUserId(user.getId());
		noti.setMessage("Password Reset Successfull");
		noti.setStatus(NotificationStatus.UNREAD);
		noti.setTimestamp(LocalDateTime.now());
		noti.setAction("Password Reset");
		notificationService.sendNotification(noti);
				return new ResponseDTO("Password changed successfully.");

	}

	
	// Expire OTP delete Method in 5 min
	@Scheduled (fixedRate = 60000)
	public void removeExpiredOTPs() {
		LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
		List<OTP>expiredOtps = otpRepository.findByCreationTimeBefore(expiry);
		if(!expiredOtps.isEmpty()) {
			otpRepository.deleteAll(expiredOtps);
			System.out.println("Removed "+expiredOtps.size()+" expired OTPs.");
		}
	}
	
	
}
