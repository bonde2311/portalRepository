package com.bsquare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bsquare.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long>{

}
