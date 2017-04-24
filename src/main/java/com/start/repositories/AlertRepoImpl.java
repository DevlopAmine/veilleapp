package com.start.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import com.start.models.*;


public  class AlertRepoImpl  {
	
	@Autowired
	static 
	MongoTemplate mongoTemplate;

	
	public static void findByIns() {
		
		//List<Alert> listA =  mongoTemplate.find(query(where("instance.$id").is(new ObjectId("58e7c8fe3b87f01dacf5fed6"))),Alert.class);
		
		List<Alert> listA =  mongoTemplate.find(query(where("descA").is("messi")),Alert.class);
		for (Alert a : listA) {
			System.out.println(a.getDescA() +"  "+a.getId());
			
		}
		//return listA;
	}

}
