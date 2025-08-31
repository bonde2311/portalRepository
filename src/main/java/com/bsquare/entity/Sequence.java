//package com.bsquare.entity;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.Data;
//@Data
//
//@Document(collection = "sequence")
//public class Sequence {  // class is used for sequence of ID and Jobs
//	
//	@Id
//	private String id;
//	
//	private long seq;
//	
//	
//}
package com.bsquare.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sequence")
public class Sequence { // This class is used for maintaining sequence counters for IDs

	@Id
	private String id; // Name of the sequence, like "user_sequence", "job_sequence", etc.

	private long seq; // The current value of the sequence
}
