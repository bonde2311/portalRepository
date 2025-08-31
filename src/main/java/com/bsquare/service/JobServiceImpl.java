package com.bsquare.service;

import com.bsquare.utility.ExceptionControllerAdvice;
import com.bsquare.utility.Utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsquare.dto.ApplicantDTO;
import com.bsquare.dto.Application;
import com.bsquare.dto.ApplicationStatus;
import com.bsquare.dto.JobDTO;
import com.bsquare.dto.JobStatus;
import com.bsquare.dto.NotificationDTO;
import com.bsquare.entity.Applicant;
import com.bsquare.entity.Job;
import com.bsquare.exception.JobPortalException;
import com.bsquare.repository.JobRepository;

@Service("jobService")
public class JobServiceImpl implements JobService {

	private final ExceptionControllerAdvice exceptionControllerAdvice;

	private final Utilities utilities;

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private JobRepository jobRepository;

	JobServiceImpl(Utilities utilities, ExceptionControllerAdvice exceptionControllerAdvice) {
		this.utilities = utilities;
		this.exceptionControllerAdvice = exceptionControllerAdvice;
	}

	
	
	@Override
	public JobDTO postJob(JobDTO jobDTO) throws JobPortalException {
		if(jobDTO.getId() == null || jobDTO.getId() == 0) {
			jobDTO.setId(Utilities.getNextSequence("jobs"));
			jobDTO.setPostTime(LocalDateTime.now());
			
			// Notification implementation for posting job and route to a posted job
			NotificationDTO notiDTO = new NotificationDTO();
			notiDTO.setAction("Job Posted");
			notiDTO.setMessage("Job Posted Successfully for "+ jobDTO.getJobTitle() + " at " + jobDTO.getCompany());
			
			notiDTO.setUserId(jobDTO.getPostedBy());
			notiDTO.setRoute("/posted-by" + jobDTO.getId());
			notificationService.sendNotification(notiDTO);
			
			
		}else {
			Job job = jobRepository.findById(jobDTO.getId()).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
			if (job.getJobStatus().equals(JobStatus.DRAFT)||jobDTO.getJobStatus().equals(JobStatus.CLOSED))jobDTO.setPostTime(LocalDateTime.now()); {
				
			}
		}
		return jobRepository.save(jobDTO.toEntity()).toDTO();

	}
	
	
	@Override
	public List<JobDTO> getAllJobs() {
		return jobRepository.findAll().stream().map((x) -> x.toDTO()).toList();
	}

	
	
	
	
	@Override
	public JobDTO getJob(Long id) throws JobPortalException {
		return jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")).toDTO();
	}

	
	
	
	@Override
	public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException {
		Job job = jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
		
		List<Applicant> applicants = job.getApplicants();
		if (applicants == null)
			applicants = new ArrayList<>();
		
		if (applicants.stream().filter((x) -> x.getApplicantId() == applicantDTO.getApplicantId()).toList().size() > 0)
			throw new JobPortalException("JOB_APPLIED_ALREADY");
		
		applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
		
		applicants.add(applicantDTO.toEntity());
		
		job.setApplicants(applicants);
		
		jobRepository.save(job);

	}

	
	
	@Override
	public List<JobDTO> getJobsPostedBy(Long id) {
		return jobRepository.findByPostedBy(id).stream().map((x) -> x.toDTO()).toList();
	}

	
	
	@Override
	public void changeAppStatus(Application application) throws JobPortalException {
		Job job = jobRepository.findById(application.getId())
				.orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
		
		List<Applicant> applicants = job.getApplicants().stream().map((x) -> {
			if (application.getApplicantId() == x.getApplicantId()) {
				x.setApplicationStatus(application.getApplicationStatus());
				if (application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING))
					x.setInterviewTime(application.getInterviewTime());
				
				// Notification implementation 
				NotificationDTO notiDTO = new NotificationDTO();

				notiDTO.setAction("Interview Scheduled");

				notiDTO.setMessage("Interview scheduled for job id: "+application.getId());
				
				notiDTO.setUserId(application.getApplicantId());
				
				// Route Handling
				notiDTO.setRoute("/job-history");
				
				try {
					notificationService.sendNotification(notiDTO);
				} catch (JobPortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return x;
		}).toList();
		
		job.setApplicants(applicants);
		
		jobRepository.save(job);
	}

	
	
}