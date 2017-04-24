package com.start.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.start.daoservices.AlertService;
import com.start.daoservices.CustomerService;
import com.start.daoservices.InstanceService;
import com.start.models.Alert;
import com.start.models.Instance;
import com.start.models.User;
 

/**
 * @author amine
 *
 */

@RestController
@RequestMapping("/it")
public class ToDBController {
	 private static final Logger log = LoggerFactory.getLogger(ToDBController.class);
	
	@Autowired
	  private InstanceService instServ;
	@Autowired
	  private AlertService alertServ;
	@Autowired
	  private CustomerService customServ;
  /**
	   * Create Instance 
	 * @param InstanceMap
	 * @return Map<String,Object>
	 */
	  @RequestMapping(method = RequestMethod.POST)
	  public void createInstance(@RequestBody Map<String, Object> InstanceMap){
	    
		instServ.saveInstance(InstanceMap);
		
	  }
	
	@RequestMapping(method = RequestMethod.POST,value="newcustom")
	  public void createCustomer(@RequestBody HashMap<String, Object> CustomMap){
		
		 customServ.createCustomer(CustomMap);
		
	  }
	  
	  @RequestMapping(method = RequestMethod.GET,value="twlist/{descI}")
	  public ResponseEntity<Alert> insertFromListSN(@PathVariable("descI") String descI){
		
		  Alert alert = new Alert(new ObjectId(),"fashion_alert");
		  alertServ.saveTwAlert(alert,descI);
	
	 	return new ResponseEntity<Alert>(alert, HttpStatus.CREATED);
	  }
	  
	  @RequestMapping(method = RequestMethod.DELETE,value="/{alertId}")
	  public ResponseEntity<String> deleteAlert(@PathVariable("alertId") String alertId){  
		
		alertServ.deleteAlert(alertId);
		    
		
	    return new ResponseEntity<String>("Alert deleted successfully", HttpStatus.NO_CONTENT);
	  }
	  
	 @RequestMapping(method = RequestMethod.GET,value="/{InstanceId}",produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody ResponseEntity<List<Alert>> ShowAlertsByInstance(@PathVariable("InstanceId") String InstanceId ){  
		
		 List<Alert> alerts = alertServ.findAlertsByInstanceId(new ObjectId(InstanceId));
		
		return new ResponseEntity<List<Alert>>(alerts, HttpStatus.OK);
	  }
	
	 /*
	  *  get Posts from FB and store them to MongoDB
	  * 
	  */
	 @RequestMapping(method = RequestMethod.GET,value="fbList/{descI}")
	  public ResponseEntity<Alert> insertFromFBList(@PathVariable("descI") String descI){
		
		 Alert a1 = new Alert(new ObjectId(),"Visit Morocco2");
	     alertServ.saveFBAlert(a1,descI);
	     
	     return new ResponseEntity<Alert>(a1, HttpStatus.CREATED);
	  
	  }
	  
	 /*
	  *  get Posts from Google custom search and store them to MongoDB
	  * 
	  */
	 @RequestMapping(method = RequestMethod.GET,value="cseList/{descI}")
	  public ResponseEntity<Alert> insertCSEList(@PathVariable("descI") String descI){
		
		 Alert a1 = new Alert(new ObjectId(),"Spain alert");
		 
	     alertServ.saveGgAlert(a1,descI);
	     
	     return new ResponseEntity<Alert>(a1, HttpStatus.CREATED);
	  
	  }
	
	 @RequestMapping(method = RequestMethod.GET,value="t")
	  public void tstB(){
		
		 System.out.println(alertServ.issetAlert("Visit Morocco2"));
	     
	    // return new ResponseEntity<Alert>(a1, HttpStatus.CREATED);
	  
	  }
	 
	 @RequestMapping(method = RequestMethod.DELETE,value="delInst/{InstId}")
	  public ResponseEntity<String> dropAlerts(@PathVariable("InstId") String InstId){  
		instServ.removeInstance(new ObjectId(InstId));
			    
	    return new ResponseEntity<String>("Alerts deleted successfully", HttpStatus.NO_CONTENT);
	  }
	 
	 @RequestMapping(method = RequestMethod.GET,value="ta")
		public void tstA(){
			String descA="Visit Morocco2";
			//alertServ.findAlertId(descA);
		   
		  // return new ResponseEntity<Alert>(a1, HttpStatus.CREATED);
		
		}
	 
	 @RequestMapping(method = RequestMethod.GET,value="instances/{CostId}",produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody ResponseEntity<List<Instance>> ShowInstancesByCostumer(@PathVariable("CostId") String CostId ){  
		
		 List<Instance> instances = instServ.findInstancesByCustomerId(new ObjectId(CostId));
		
		return new ResponseEntity<List<Instance>>(instances, HttpStatus.OK);
	  }
	 
	 @RequestMapping(method = RequestMethod.GET,value="users/{desc}",produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody ResponseEntity<List<User>> ShowUsersByCostumer(@PathVariable("desc") String desc ){  
		
		 List<User> users = customServ.findUsersByCustomer(desc);
		System.out.println(users.toString());
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	  }
}






