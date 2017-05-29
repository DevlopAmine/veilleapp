package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.client.RestTemplate;

import com.start.repositories.AlertRepository;
import com.start.services.FBService;
import com.start.services.NewTwServiceImpl;
import com.start.services.NwGgServiceImpl;
import com.google.api.services.customsearch.model.Result;
import com.restfb.types.Post;
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
	@Autowired
	private NwGgServiceImpl ngw;
	@Autowired
	private NewTwServiceImpl ntw;
	@Autowired
	private FBService fbService;
	@Autowired
	private FBpageService fbPageServ; 
	
	@Override
	public void saveAlert(Alert alert,String descI) {
			
			List<SNresult> fbL = getFBdata(alert);
			Map<String, List<Map<String, Object>>> pageMap;
			List<SNresult> tweetL = filterData(ntw.getFiltredData(alert));
			List<Result> Ggresult= ngw.searchedResults(alert);
			List<SNresult> Ggl = new ArrayList<>();
			
			for (Result r : Ggresult) {
				 try {
					  pageMap = r.getPagemap();
					  Ggl.add(new SNresult(r.getCacheId(), r.getTitle(), r.getLink(),pageMap.get("cse_image").get(0).get("src").toString()));
				} catch (Exception e) {
					System.err.println("not foud cse_image pageMap");
				}
				
			}
			List<SNresult> nList = Stream.concat(tweetL.stream(), Ggl.stream()).collect(Collectors.toList());
			List<SNresult> newList = Stream.concat(nList.stream(),fbL.stream()).collect(Collectors.toList());
		
			
			int c=0;
			List<AlertSource> list = new ArrayList<AlertSource>();
			list.clear();
			for (SNresult sNresult : newList) {
				 list.add(new AlertSource(sNresult.getIdR(),sNresult.getText(),sNresult.getUrl(),sNresult.getUrl_img()));
				 System.err.println(list.get(c).getLink()+"; "+list.get(c).getText()+"; "+list.get(c).getLink_img());
				 c++;
				 
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
	public Alert findAlertByDesc(String descA) {
		Alert al =  mongoTemplate.findOne(query(where("descA").is(descA)),Alert.class);
		
		return al;
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
	@Override
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
	
	 private List<SNresult> filterData(List<Tweet> tweetList)
	 {
		 List<SNresult> resL = new ArrayList<>();
		    
		  
			for (Tweet tweet : tweetList) 
			{
				Iterator<MediaEntity> mediaI = tweet.getEntities().getMedia().listIterator();
				
				if(mediaI.equals(null))
				{
					System.out.println("null media");
					
				}
				while(mediaI.hasNext())
				{
				 MediaEntity media = mediaI.next();
				 resL.add(new SNresult(tweet.getIdStr(),tweet.getText(), media.getUrl(),media.getDisplayUrl()));
				 //System.out.println(resL.get(c).toString());
				 
				}
			
			}
			return resL;
	 }
	 
	 private List<SNresult> getFBdata(Alert alert)
	 {
		    String pageId = fbPageServ.getPageId("djsnake.fr");
			List<Post> listP = fbService.getLowerCaseKeyword(alert.getDescA(), pageId);
			List<SNresult> resultfb= new ArrayList<>();
			if(listP.size()!=0)
			{
			SNresult sc = new SNresult(listP.get(0).getId(),listP.get(0).getMessage(),listP.get(0).getLink(),listP.get(0).getPicture());
			SNresult sc1 = new SNresult(listP.get(1).getId(),listP.get(1).getMessage(),listP.get(1).getLink(),listP.get(1).getPicture());
			resultfb.add(sc);resultfb.add(sc1);
			}
			else
			{
				System.err.println("No data found");
			}
			
			
			return resultfb;
		 
	 }
	
	
}
