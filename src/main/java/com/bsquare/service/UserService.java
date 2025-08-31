package com.bsquare.service;

import com.bsquare.dto.LoginDTO;
import com.bsquare.dto.ResponseDTO;
import com.bsquare.dto.UserDTO;
import com.bsquare.exception.JobPortalException;

import jakarta.validation.Valid;

// Service interface for user-related operations
public interface UserService {
    
    // Method to register a new user
    // Takes UserDTO as input and returns the saved UserDTO
    // Throws JobPortalException in case of registration failures
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException;
    
    // Method to authenticate a user during login
    // Takes LoginDTO (login credentials) as input and returns the matched UserDTO
    // Throws JobPortalException if login fails (e.g., wrong email/password)
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException;

	public Boolean sendOtp(String email) throws  Exception;

	public Boolean verifyOtp(String email, String otp) throws JobPortalException;

	public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException;

}
