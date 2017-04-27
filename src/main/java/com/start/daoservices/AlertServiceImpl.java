package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.start.repositories.AlertRepository;
import com.google.api.services.customsearch.model.Result;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.SNresult;


/**
 * @author amine
 *
 */
public class AlertServiceImpl implements AlertService {

	@Autowired 
	private AlertRepository alertRepo;
	@Autowired 
	MongoTemplate mongoTemplate;
	@Autowired
	private InstanceService InstServ;
	@Override
	public void saveAlert(Alert alert,String descI) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<List<SNresult>> twResponse =
			        restTemplate.exchange("http://localhost:8080/svc/v1/tweets/fd",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<SNresult>>() {
			            });
		 ResponseEntity<List<Result>> GgResponse =
			        restTemplate.exchange("http://localhost:8080/customse/filter",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Result>>() {
			            });
		 
		 ResponseEntity<List<SNresult>> FbResponse =
			        restTemplate.exchange("http://localhost:8080/svc/v1/fb/lower",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<SNresult>>() {
			            });
			List<SNresult> fbL = FbResponse.getBody();
			List<SNresult> tweetL = twResponse.getBody();
			List<Result> Ggresult= GgResponse.getBody();
			
			List<SNresult> Ggl = new ArrayList<>();
			for (Result r : Ggresult) {
				Ggl.add(new SNresult(r.getCacheId(), r.getTitle(), r.getLink()));
			}
			List<SNresult> nList = Stream.concat(tweetL.stream(), Ggl.stream()).collect(Collectors.toList());
			List<SNresult> newList = Stream.concat(nList.stream(),fbL.stream()).collect(Collectors.toList());
		
			
			int c=0;
			List<AlertSource> list = new ArrayList<AlertSource>();
			list.clear();
			for (SNresult sNresult : newList) {
				 list.add(new AlertSource(sNresult.getIdR(),sNresult.getText(),sNresult.getUrl()));
				 System.err.println(list.get(c).getLink()+"; "+list.get(c).getText());
				 c++;
				 
			}
			
			persistAlert(alert, list,descI);
			
	    
		
	}
@Override
	public void saveFBAlert(Alert alert,String descI) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SNresult>> FbResponse =
			        restTemplate.exchange("http://localhost:8080/svc/v1/fb/lower",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<SNresult>>() {
			            });
			List<SNresult> fbL = FbResponse.getBody();
			List<AlertSource> list = new ArrayList<AlertSource>();
			
			for (SNresult sNresult : fbL) {
				System.out.println(sNresult.getText());
				list.add(new AlertSource(sNresult.getIdR(),sNresult.getText(),sNresult.getUrl()));
			}
			persistAlert(alert, list,descI);
		
	}

	@Override
	public void saveGgAlert(Alert alert,String descI) {
		RestTemplate restTemplate = new RestTemplate();
		  ResponseEntity<List<SNresult>> GgResponse =
			        restTemplate.exchange("http://localhost:8080/customse/data",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<SNresult>>() {
			            });
			List<SNresult> gGL = GgResponse.getBody();
			List<AlertSource> list = new ArrayList<AlertSource>();
			
			for (SNresult sNresult : gGL) {
				System.out.println(sNresult.getText());
				
				 list.add(new AlertSource(sNresult.getIdR(),sNresult.getText(),sNresult.getUrl()));
			}
			persistAlert(alert, list,descI);
		
	}
	
	@Override
	public void deleteAlert(String alertId) {
		alertRepo.delete(alertId);
		
	}

	@Override
	public List<Alert> findAlertsByInstanceId(ObjectId oId) {
		List<Alert> listA =  mongoTemplate.find(query(where("instanceId").is(oId)),Alert.class);
		for (Alert a : listA) {
			System.out.println(a.getDescA() +"  "+a.getId());
			
		}
		return listA;
	}
	
	@Override
	public  boolean issetAlert(String desc) {
		List<Alert> listA =  mongoTemplate.find(query(where("descA").is(desc)),Alert.class);
		
		for (Alert a : listA) {
			System.out.println(a.getDescA() +"  "+a.getId());
			
		}
		if(listA.size()>0)
			return true;
		
		else return false;
	}
	
	@Override
	public  Alert getAlert(String desc) {
		Alert a =  mongoTemplate.findOne(query(where("descA").is(desc)),Alert.class);
		return a;
	}
	public void persistAlert(Alert alert,List<AlertSource> list,String descI)
	{
		if(issetAlert(alert.getDescA()))
		{
			Update update = new Update();
			update.pushAll("alertsources", list.toArray());
			mongoTemplate.updateFirst(query(where("descA").is(alert.getDescA())), update, Alert.class);
		}
	 
		else
		{
			
			  alert.setInstanceId(InstServ.findInstanceId(descI));
			  alert.setAlertsources(list);
			  alertRepo.save(alert);
		}
		
	}
	
	/* (non-Javadoc)
	 * Permet de recuperer l'Id de l'instance lié à la description de l'Alert désigné
	 
	@Override
	public ObjectId  findAlertId(String descA) {
		
		 Alert alert = mongoTemplate.findOne(query(where("descA").is(descA)),Alert.class,"alert");
		 System.err.println(alert.getInstance().getId());
		 return alert.getInstance().getId();
		
	}
*/
	
	
}
