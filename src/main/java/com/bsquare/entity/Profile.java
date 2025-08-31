package com.bsquare.entity;

import java.util.Base64;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bsquare.dto.Certification;
import com.bsquare.dto.Experience;
import com.bsquare.dto.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String jobTitle;
	private String company;
	private String location;
	private String about;
	private byte[] profilePic;
	private byte[] bannerImg;
	private long totalExp;
	private List<String> skills;
	private List<Experience> experiences;
	private List<Certification> certifications;
	private List<Long>savedJobs;


	public ProfileDTO toDTO() {
		return new ProfileDTO(this.id, this.firstName, this.lastName, this.email, this.jobTitle,
			this.company, this.location, this.about,
			this.profilePic != null ? Base64.getEncoder().encodeToString(this.profilePic) : null,
			        this.bannerImg != null ? Base64.getEncoder().encodeToString(this.bannerImg) : null,
			this.totalExp,this.skills, this.experiences, this.certifications, this.savedJobs);
	}

}