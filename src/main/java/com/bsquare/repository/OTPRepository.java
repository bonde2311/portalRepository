package com.bsquare.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bsquare.entity.OTP;

public interface OTPRepository extends MongoRepository<OTP, String> {

	List<OTP>findByCreationTimeBefore(LocalDateTime expiry);
}
