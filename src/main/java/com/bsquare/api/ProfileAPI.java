package com.bsquare.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsquare.dto.ProfileDTO;
import com.bsquare.exception.JobPortalException;
import com.bsquare.service.ProfileService;

@RestController // Marks this class as a Spring REST controller
@CrossOrigin // Allows Cross-Origin requests (frontend can call APIs without CORS issues)
@Validated // Enables validation on request payloads
@RequestMapping("/profiles") // Base URL for all endpoints inside this controller (e.g., /users/register)
public class ProfileAPI {

	@Autowired // Field injection of ProfileService (actual service used for method calls)
	private ProfileService profileService;

	// API for GET profile
	@GetMapping("/get/{id}")
	public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws 
	JobPortalException {
		return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
	}

	// API for get All Profile
		@GetMapping("/getAllProfiles")
		public ResponseEntity<List<ProfileDTO>> getAllProfiles() throws 
		JobPortalException {
			return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
		}
	
	// API for update profile
	@PutMapping("/update")
	public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws 
	JobPortalException {
		return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
	}

}
