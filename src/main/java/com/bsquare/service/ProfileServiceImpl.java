package com.bsquare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsquare.dto.ProfileDTO;
import com.bsquare.entity.Profile;
import com.bsquare.exception.JobPortalException;
import com.bsquare.repository.ProfileRepository;
import com.bsquare.utility.ExceptionControllerAdvice;
import com.bsquare.utility.Utilities;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    private final ExceptionControllerAdvice exceptionControllerAdvice;

	@Autowired
	private ProfileRepository profileRepository;

    ProfileServiceImpl(ExceptionControllerAdvice exceptionControllerAdvice) {
        this.exceptionControllerAdvice = exceptionControllerAdvice;}

	@Override
	public Long createProfile(String email) throws JobPortalException {
		Profile profile = new Profile();
		profile.setId(Utilities.getNextSequence("profiles"));
		profile.setEmail(email);
		profile.setSkills(new ArrayList<>());
		profile.setExperiences(new ArrayList<>());
		profile.setCertifications(new ArrayList<>());

		profileRepository.save(profile);

		return profile.getId();
	}

	@Override
	public ProfileDTO getProfile(Long id) throws JobPortalException {
		return profileRepository.findById(id).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND")).toDTO();
	}

	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
		profileRepository.findById(profileDTO.getId()).orElseThrow(() -> 
		new JobPortalException("PROFILE_NOT_FOUND"));
		profileRepository.save(profileDTO.toEnitity());
		return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfiles() {
		return profileRepository.findAll().stream().map((x)->x.toDTO()).toList();
	}

}
