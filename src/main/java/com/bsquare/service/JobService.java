package com.bsquare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bsquare.dto.ApplicantDTO;
import com.bsquare.dto.Application;
import com.bsquare.dto.JobDTO;
import com.bsquare.exception.JobPortalException;

import jakarta.validation.Valid;


public interface JobService {

	public JobDTO postJob(JobDTO jobDTO) throws JobPortalException;

	public List<JobDTO> getAllJobs();

	public JobDTO getJob(Long id) throws JobPortalException;

	public void applyJob(Long id, ApplicantDTO applicantDTO)throws JobPortalException;

	public List<JobDTO> getJobsPostedBy(Long id);

	public void changeAppStatus(Application application) throws JobPortalException;


	
	
}
