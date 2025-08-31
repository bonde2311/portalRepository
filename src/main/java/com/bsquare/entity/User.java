package com.bsquare.entity;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bsquare.dto.AccountType;
import com.bsquare.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class User {

	@Id
	private long id;

	private String firstName;

	private String lastName;

	@Indexed(unique = true) // Used for Unique Email Id
	private String email;

	private String password;

	private AccountType accountType;

	private Long profileId;

	public UserDTO toDTO() {
		return new UserDTO(this.id, this.firstName, this.lastName, this.email, this.password, this.accountType,
				this.profileId);
	}
}
