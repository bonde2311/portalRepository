package com.bsquare.dto;

import java.util.Base64;
import java.util.List;

import com.bsquare.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String jobTitle;
	private String company;
	private String location;
	private String about;
	private String profilePic;
	private String bannerImg;
	private long totalExp;
	private List<String> skills;
	private List<Experience> experiences;
	private List<Certification> certifications;
	private List<Long>savedJobs;

	public Profile toEnitity() {
		return new Profile(this.id, this.firstName, this.lastName,this.email, this.jobTitle, this.company, this.location, this.about,
				this.profilePic != null ? Base64.getDecoder().decode(this.profilePic) : null,
				this.bannerImg != null ? Base64.getDecoder().decode(this.bannerImg) : null,this.totalExp, this.skills,
				this.experiences, this.certifications,this.savedJobs);
	}
}
