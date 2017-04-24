package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.start.models.Customer;
import com.start.models.User;
import com.start.repositories.CustomRepository;

public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomRepository customRep;
	@Autowired 
	MongoTemplate mongoTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public void createCustomer(HashMap<String, Object> CustomMap) {
		  List<ObjectId> objIdList = new ArrayList<>();
		  List<Object> list = (List<Object>) CustomMap.get("inst_ids");
		  List<User> listU = (List<User>) CustomMap.get("users");
		  for (int i=0;i<list.size();i++) {
			objIdList.add(new ObjectId(String.valueOf(list.get(i))));
			 //System.out.println( String.valueOf(list.get(i)));
		}
		
		  Customer custom = new Customer(CustomMap.get("description").toString());
		  custom.setListIds(objIdList);
		  custom.setListusers(listU);
		customRep.save(custom);
		
	}
	@Override
	public List<User> findUsersByCustomer(String description) {
		Customer listcost =  mongoTemplate.findOne(query(where("description").is(description)),Customer.class);
		List<User> listusers = listcost.getListusers();
		return listusers;
		
	}

}
