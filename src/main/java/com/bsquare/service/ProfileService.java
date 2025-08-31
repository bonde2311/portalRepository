package com.bsquare.service;

import java.util.List;

import com.bsquare.dto.ProfileDTO;
import com.bsquare.exception.JobPortalException;

public interface ProfileService {

	public Long createProfile(String email) throws JobPortalException;
	public ProfileDTO getProfile(Long id) throws JobPortalException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
	public List<ProfileDTO> getAllProfiles();
	
}
