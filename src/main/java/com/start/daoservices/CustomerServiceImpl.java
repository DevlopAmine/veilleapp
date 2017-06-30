package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.start.models.Alert;
import com.start.models.Customer;
import com.start.models.Instance;
import com.start.models.User;
import com.start.repositories.CustomRepository;

public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomRepository customRep;
	@Autowired 
	MongoTemplate mongoTemplate;
	@Autowired
	private AlertService alertServ;
	
	@SuppressWarnings("unchecked")
	@Override
	public void createCustomer(Customer Customer) {
		int num = (int)(Math.random() * ((100 - 0) + 1));
		Customer.setIdL(num);  
		customRep.save(Customer);
		
	}
	@Override
	public String addInstToCustomer(Instance inst) {
		String resp ="";
		if(issetCustomer(inst.getCustomer_id()))
		{
			Update update = new Update();
			update.push("inst_ids", inst.getId());
			mongoTemplate.updateFirst(query(where("_id").is(inst.getCustomer_id())), update, Customer.class);
			resp ="Alert updated";
		}
		else
		{
			resp ="there is no Cistomer with this cosId";
		}
		return resp;
		
	}
	
	@Override
	public List<User> findUsersByCustomer(String description) {
		Customer listcost =  mongoTemplate.findOne(query(where("description").is(description)),Customer.class);
		List<User> listusers = listcost.getListusers();
		return listusers;
		
	}
	
	@Override
	public Customer getCustomer(String name) {
		Customer client = mongoTemplate.findOne(query(where("name").is(name)), Customer.class, "customer");
		return client;
		
	}
	@Override
	public Customer getCustomerByUsername(String username) {
		Customer client = mongoTemplate.findOne(query(where("username").is(username)), Customer.class, "customer");
		System.err.println(client.toString());
		return client;
		
	}
	
	@Override
	public void update(Customer cust) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(cust.getEmail()));
		query.fields().include("email");

		Update update = new Update();
		update.set("name", cust.getName());
		update.set("tel", cust.getTel());
		update.set("description", cust.getDescription());

		mongoTemplate.updateFirst(query, update,Customer.class);
		
	}
	@Override
	public List<Alert> findAlertsByCustomer(String nom) {
		
		Customer client = getCustomer(nom);
		List<ObjectId> instIds = client.getListIds();
		List<Alert> alerts = new ArrayList<Alert>();
	
		for (ObjectId objectId : instIds) {
			alerts.addAll(alertServ.findAlertsByInstanceId(objectId));
			
		}
		
		return alerts;
		
	}
	@Override
	public  boolean issetCustomer(ObjectId cosId) {
		Customer cl =  mongoTemplate.findOne(query(where("_id").is(cosId)),Customer.class);
		
		if(cl.equals(null))
			return false;
		
		else return true;
	}

}
