package com.bsquare.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bsquare.entity.User;


// 
public interface UserRepository extends MongoRepository<User, Long>{
	
	// method for error messge when duplicate email is comes
	
	public Optional<User> findByEmail(String email);
	

}
