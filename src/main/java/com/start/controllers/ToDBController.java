package com.start.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.http.entity.ContentType;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfb.types.Page;
import com.start.daoservices.AlertService;
import com.start.daoservices.CustomerService;
import com.start.daoservices.FBpageService;
import com.start.daoservices.InstanceService;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.Customer;
import com.start.models.FbPage;
import com.start.models.Instance;
import com.start.models.User;
import com.start.services.FileSessionService;
import com.start.services.NewTwServiceImpl;
 

/**
 * @author amine
 *
 */

@RestController
@RequestMapping("/it")
@CrossOrigin
public class ToDBController {
	 private static final Logger log = LoggerFactory.getLogger(ToDBController.class);
	
	@Autowired
	  private InstanceService instServ;
	@Autowired
	  private AlertService alertServ;
	@Autowired
	  private CustomerService customServ;
	@Autowired
	  private FBpageService fbPageServ;
	 @Autowired
	    private Customer cl;
	 @Autowired
		private FileSessionService fileSessionServ;
	
  /**
	   * Create Instance 
	 * @param InstanceMap
	 * @return Map<String,Object>
	 */
	  @RequestMapping(method = RequestMethod.POST,value="creatInst",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public void createInstance(@RequestBody Instance instance){
		
		  
		cl = fileSessionServ.readSession();
		instance.setCustomer_id(cl.getId());
		instServ.saveInstance(instance);
		customServ.addInstToCustomer(instance);
		
	  }
	
	@RequestMapping(method = RequestMethod.POST,value="newcustom")
	  public void createCustomer(@RequestBody Customer Customer){
		
		 customServ.createCustomer(Customer);
		
	  }
	  
	  @RequestMapping(method = RequestMethod.POST,value="critereAlt",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Alert> createAlert(@RequestBody Alert alert){
		String resp = null;
		ResponseEntity<Alert> res = new ResponseEntity<Alert>(alert, HttpStatus.NO_CONTENT);;
		if(alert != null)
			{
			resp = alertServ.saveAlert(alert,alert.getDescI());
			if(resp=="Alert updated" || resp =="Alert added")
				res = new ResponseEntity<Alert>(alert, HttpStatus.CREATED);
			else res = new ResponseEntity<Alert>(alert, HttpStatus.NOT_FOUND);
			}
		  
	
	 	return res;
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
	 
	 @RequestMapping(method = RequestMethod.POST,value="fbpages",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Boolean> insertFbPage(@RequestBody(required=false) Map<String, Object> AlertMap )
	 {
		 ResponseEntity<Boolean> resp;
		
		 String descA = AlertMap.get("parent").toString();
		 if(alertServ.getAlert(descA)!=null)
			 {
			 System.err.println("cette alerte existe deja, ses pages FB existent dans la BD  !!!");
			 resp =  new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
			 //fbPageServ.savePage(alertServ.getAlert(descA));
			 }
			
		 else{
			System.err.println( "here the descI:  "+AlertMap.get("descI").toString() );
			 
			 Alert alert = new Alert(new ObjectId(),descA); 
			 alert.setInstanceId(instServ.findInstanceId(AlertMap.get("descI").toString()));
			 fbPageServ.savePage(alert);
			 resp =  new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		 }
		 
		 return resp;
	 
	  } 
	/* @RequestMapping(method = RequestMethod.GET,value="getfbpage/{parent}",produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody ResponseEntity<List<FbPage>> ShowFBpages(@PathVariable("parent") String parent ){  
		
		 List<FbPage> pages = fbPageServ.getPagesByParent(parent);
		
		return new ResponseEntity<List<FbPage>>(pages, HttpStatus.OK);
	  }*/
	 
	 
	 @RequestMapping(method = RequestMethod.GET,value="getfbpages",produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody ResponseEntity<List<FbPage>> ShowFBpages(){  
		
		 List<FbPage> pages = fbPageServ.getPages();
		
		return new ResponseEntity<List<FbPage>>(pages, HttpStatus.OK);
	  }
	 
	 
	 @RequestMapping(method = RequestMethod.PUT,value="updateUser",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Customer> updateProfile(@RequestBody Customer cust){
		
		 
		if(cust != null)
			customServ.update(cust);
		  
	
	 	return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	  }
	 
	 @RequestMapping(method = RequestMethod.PUT,value="updateMention",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<AlertSource> updateMention(@RequestBody AlertSource als,@RequestParam (value="descA",required=true) String descA){
		ResponseEntity<AlertSource> resp;
		Object res = alertServ.UpdateAlertSourceById(als,descA);
		if(res != null)
		{
			 
			  resp = new ResponseEntity<AlertSource>(als, HttpStatus.OK);
		}
		else 
		{
			resp = new ResponseEntity<AlertSource>(als, HttpStatus.NOT_FOUND);
		}
		return resp;
		 
	  }
	 
	 
	 @RequestMapping(method = RequestMethod.DELETE,value="deleteMention",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<String> updateMention(@RequestParam (value="ids",required=true) String ids,@RequestParam (value="descA",required=true) String descA){
		ResponseEntity<String> resp;
		Object res = alertServ.deleteAlertSourceById(ids, descA);
		if(res != null)
		{
			 
			  resp = new ResponseEntity<String>(ids+" has been deleted", HttpStatus.NO_CONTENT);
		}
		else 
		{
			resp = new ResponseEntity<String>("ERROR in removing", HttpStatus.NOT_FOUND);
		}
		return resp;
		 
	  }
	


	

}






