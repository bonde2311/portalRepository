package com.bsquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BSquareJobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BSquareJobPortalApplication.class, args);
		System.out.print("\nBSquare...API {1}.jobs/postedBy/id....{2}.jobs/changeAppStatus. {3}.profiles/getAllProfiles");
	}

}
