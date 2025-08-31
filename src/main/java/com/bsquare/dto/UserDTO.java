package com.bsquare.dto;

import org.springframework.http.HttpStatus;
import com.bsquare.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getters, setters, toString, equals, and
		// hashCode
@NoArgsConstructor // Lombok annotation to generate no-arg constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor
public class UserDTO {

	private long id; // Unique identifier for the user

	@NotBlank(message = "{user.firstName.absent}")
	// Validation to ensure name is not blank; message picked from properties file
	private String firstName;

	@NotBlank(message = "{user.lastName.absent}")
	private String lastName;

	@NotBlank(message = "{user.email.absent}")
	@Email(message = "user.email.invalid")
	// Validation for correct email format
	private String email;

	@NotBlank(message = "{user.password.absent}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$", 
	message = "{user.password.invalid}")
	// Password must have:
	// - 8 to 12 characters
	// - At least one uppercase, one lowercase, one number, and one special
	// character
	private String password;

	private AccountType accountType; // Enum field to store type of account (ex: Admin, User, etc.)

	private Long profileId;

	// Converts this DTO object into an Entity object (User)
	public User toEntity() {
		return new User(this.id, this.firstName, this.lastName, this.email, this.password, this.accountType,
				this.profileId);
	}
}
