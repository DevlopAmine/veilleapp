package com.start.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.start.daoservices.AlertService;
import com.start.daoservices.InstanceService;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.Instance;

@RestController
@RequestMapping("/view")
@CrossOrigin
public class ViewController {
	private static final Logger log = LoggerFactory.getLogger(ToDBController.class);
	

	@Autowired
	  private AlertService alertServ;
	@Autowired
	  private InstanceService instServ;
	
	
	
	
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
	 
	 
	 @RequestMapping(method = RequestMethod.POST,value="critereAlt",consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Alert> insertFromListSN(@RequestBody Alert alert){
		
		
		 // filterData(ntw.getFiltredData(alert));
		  
		  
		  //alertServ.saveAlert(alert,"pop1");
	
	 	return new ResponseEntity<Alert>(alert, HttpStatus.CREATED);
	  }
	 
	 
	 @RequestMapping(method = RequestMethod.GET,value="instCost",produces = MediaType.APPLICATION_JSON_VALUE)
		public  ResponseEntity<List<Instance>> getSelectInstances(){
		//@RequestParam(value = "descI", required = false) String descI 	
		    ObjectId oid = new ObjectId("58f73cb53aefb12a44d3c739");
		    List<Instance> listI = instServ.findInstancesByCustomerId(oid);
//		    List<Instance> listI = new ArrayList<Instance>();
//		    
//		    Instance i = new Instance("pop","aa");Instance i1 = new Instance("aaa","bb");
//		    listI.add(i);listI.add(i1);
		    
		    		
		   return new ResponseEntity<List<Instance>>(listI, HttpStatus.OK);
		
		}
	 
	 
	 
	 
	 
	 

	 
}
