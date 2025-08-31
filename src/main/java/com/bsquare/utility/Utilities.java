package com.bsquare.utility;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.bsquare.api.UserAPI;
import com.bsquare.entity.Sequence;
import com.bsquare.exception.JobPortalException;

@Component // Marks this class as a Spring-managed component
public class Utilities {

    private final UserAPI userAPI;

	private static MongoOperations mongoOperation;

    Utilities(UserAPI userAPI) {
        this.userAPI = userAPI;
    } // Static MongoOperations instance for DB operations
	
	@Autowired
	public void setMongoOperation(MongoOperations mongoOperation) {
		// Setter injection for MongoOperations
		Utilities.mongoOperation = mongoOperation;
	}
	
	// Utility method to get the next sequence number for a given key
	public static long getNextSequence(String key) throws JobPortalException {
		// Create a query to find the document by _id (key)
		Query query = new Query(Criteria.where("_id").is(key));
		
		// Define the update operation to increment the 'seq' field by 1
		Update update = new Update();
		update.inc("seq", 1);
		
		// Options to return the new updated document after increment
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		
		// Perform findAndModify operation to update and fetch the sequence
		Sequence seq = mongoOperation.findAndModify(query, update, options, Sequence.class);
		
		// If no sequence document is found, throw an exception
		if (seq == null) throw new JobPortalException("Unable to get sequence id for key : " + key);
		
		// Return the updated sequence number
		return seq.getSeq(); 		
	}
	
	// genarating Mail OTP method
	
	public static String generateOTP() {
		StringBuilder otp = new StringBuilder();
		SecureRandom random = new SecureRandom();
		for(int i=0; i<6; i++)otp.append(random.nextInt(10));
		return otp.toString();
		
	}
	}

