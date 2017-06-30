package com.start.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.restfb.types.Page;
import com.start.daoservices.AlertService;
import com.start.daoservices.CustomerService;
import com.start.daoservices.InstanceService;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.Customer;
import com.start.models.FbPage;
import com.start.models.Instance;
import com.start.services.FBService;
import com.start.services.FileSessionService;

@RestController
@RequestMapping("/view")
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
public class ViewController {
	private static final Logger log = LoggerFactory.getLogger(ToDBController.class);
	

	@Autowired
	  private AlertService alertServ;
	@Autowired
	  private InstanceService instServ;
	@Autowired
	  private CustomerService costServ;
	@Autowired
	  private Customer cl;
	@Autowired
	private FileSessionService fileSessionServ;
	
	
	@RequestMapping(method = RequestMethod.GET,value="mentions/{descA}",produces = MediaType.APPLICATION_JSON_VALUE)
	  public  ResponseEntity<List<AlertSource>> ShowMentions(@PathVariable("descA") String descA ){  
		 
		 String[] hs ={"Origin", "X-Requested-With", "Content-Type", "Accept"};
		 List<String> headers = new ArrayList<>();headers.add(hs[0]);headers.add(hs[1]);headers.add(hs[2]);headers.add(hs[3]);
	
		 final HttpHeaders httpHeaders= new HttpHeaders();
		 httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		 httpHeaders.setAccessControlAllowOrigin("*");
		 httpHeaders.setAccessControlAllowHeaders(headers);
		
		
		 
		 Alert a = alertServ.findAlertByDesc(descA);
		 if(a.equals(null))
			 return new ResponseEntity<List<AlertSource>>(HttpStatus.NO_CONTENT);
		 List<AlertSource> listAs = a.getAlertsources();
		 
		return new ResponseEntity<List<AlertSource>>(listAs, HttpStatus.OK);
	  }
	 @RequestMapping(method = RequestMethod.POST,value="critereTst",consumes=MediaType.APPLICATION_JSON_VALUE)
	 public void getCriteresAlert(@RequestBody Map<String, Object> critereMap)
	 {
		 Set set = critereMap.entrySet();
		 System.out.println("form values "+set);
	 }
	 
	 
	 
	 
	 @RequestMapping(method = RequestMethod.GET,value="instCost",produces = MediaType.APPLICATION_JSON_VALUE)
		public  ResponseEntity<List<Instance>> getSelectInstances(){
			
		  //ObjectId oid = new ObjectId("58f73cb53aefb12a44d3c739");
		 //Customer cl= costServ.getCustomerByUsername(username);
		 cl = fileSessionServ.readSession();
		
		 List<Instance> listI = instServ.findInstancesByCustomerId(cl.getId());
		 
		   return new ResponseEntity<List<Instance>>(listI, HttpStatus.OK);
		
		}
	 
	 @RequestMapping(method = RequestMethod.GET,value="profile",produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Customer>  getCoordoneClient(@RequestParam (value="username",required=false) String username)
	 {
		 Customer client=costServ.getCustomerByUsername(username);
		 return new ResponseEntity<Customer>(client, HttpStatus.OK);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET,value="alertsCustomer",produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<List<Alert>>  getAlertsByClient()
	 { 
		
		 cl = fileSessionServ.readSession();
		 List<Alert> alerts = costServ.findAlertsByCustomer(cl.getName());
		 
		 return new ResponseEntity<List<Alert>>(alerts, HttpStatus.OK);
	 }
	
	
	 
	 
	
	 
	 

	 
}
