package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.Tweet;

import com.google.api.services.customsearch.model.Result;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.SNresult;
import com.start.repositories.AlertRepository;
import com.start.services.FBService;
import com.start.services.FBserviceImpl;
import com.start.services.NewTwServiceImpl;
import com.start.services.NwGgServiceImpl;


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
	
	final static Logger logger = Logger.getLogger(AlertServiceImpl.class);
	@Override
	public String saveAlert(Alert alert,String descI) {
			
			List<SNresult> fbL = getFBdata(alert);
		    //List<SNresult> fbL =null;
			Map<String, List<Map<String, Object>>> pageMap;
			List<SNresult> tweetL = filterData(ntw.getFiltredData(alert));
			List<Result> Ggresult= ngw.searchedResults(alert);
			List<SNresult> Ggl = new ArrayList<>();
			AlertSource al = new AlertSource();
			
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
				 al = new AlertSource(sNresult.getIdR(),sNresult.getText(),sNresult.getUrl(),sNresult.getUrl_img());
				 al.setDate_creation(sNresult.getDate_creation());
				 al.setLikes_count(sNresult.getLikes_count());
				 al.setComts_count(sNresult.getComts_count());
				 al.setShares_count(sNresult.getShares_count());
				 al.setAvis("");
				 
				 list.add(al);
				 System.err.println(list.get(c).getLink()+"; "+list.get(c).getText()+"; "+list.get(c).getLink_img());
				 c++;
				 
			}
			
			return persistAlert(alert, list,descI);
			
	}

	@Override
	public void deleteAlert(String alertId) {
		alertRepo.delete(alertId);
		
	}

	@Override
	public List<Alert> findAlertsByInstanceId(ObjectId oId) {
		List<Alert> listA =  mongoTemplate.find(query(where("instanceId").is(oId)),Alert.class);
		if(listA.isEmpty()) System.err.println("no alerts with this Id: "+oId);
		
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
	public String persistAlert(Alert alert,List<AlertSource> list,String descI)
	{
		String resp="";
		if(issetAlert(alert.getDescA()))
		{
			Update update = new Update();
			update.pushAll("alertsources", list.toArray());
			mongoTemplate.updateFirst(query(where("descA").is(alert.getDescA())), update, Alert.class);
			resp ="Alert updated";
		}
	 
		else
		{
			
			  alert.setInstanceId(InstServ.findInstanceId(descI));
			  alert.setAlertsources(list);
			  alertRepo.save(alert);
			  resp="Alert added";
		}
		return resp;
		
	}
	@Override
	 public List<SNresult> filterData(List<Tweet> tweetList)
	 {
		 List<SNresult> resL = new ArrayList<>();
		 SNresult sn;  
		 String mediaurl="";
		 List<MediaEntity> mediaI;
		 
		 for (Tweet tweet : tweetList) 
			{
			  mediaI =  tweet.getEntities().getMedia();
			 if(mediaI.size()==0)
				 mediaurl="";
			 else
			 {
				 for(MediaEntity m :mediaI)
					 mediaurl=m.getMediaSecureUrl();
				
			 }
			 sn = new  SNresult(tweet.getIdStr(),tweet.getText(),"https://twitter.com/"+tweet.getFromUser()+"/status/"+tweet.getIdStr()
			 ,mediaurl+":small");
			 sn.setDate_creation(FBserviceImpl.formatDate(tweet.getCreatedAt()) );
			 sn.setLikes_count(tweet.getFavoriteCount());
			 sn.setShares_count(tweet.getRetweetCount());
			 resL.add(sn);
			 	 
			}
		 
		
		 return resL;}
	 
	 private List<SNresult> getFBdata(Alert alert)
	 {
		    String pageId = fbPageServ.getPageId(alert.getFbPage());
			List<SNresult> listSN = fbService.requete(alert.getDescA(), pageId);
			List<SNresult> newList = new ArrayList<>();
			/*int i=0;
			do {
				i++;
				newList.add(listSN.get(i));
				
			} while (i<30);
				
				
			logger.info("post of FB sent To DB  "+i);*/
			return listSN;
		 
	 }
	@Override
	 public WriteResult UpdateAlertSourceById(AlertSource als,String descA) {
		WriteResult writeResult = null;
		 Query query = new Query();
         query.addCriteria(Criteria.where("descA").is(descA).and("alertsources.ids").is(als.getIds()) );
         Update update = new Update();
         update.set("alertsources.$.avis", als.getAvis());
         
         try {

             System.out.println(query.toString());
             System.out.println(update.toString());
             writeResult =  mongoTemplate.updateMulti(query, update, Alert.class, "alert");
         if (writeResult != null) {

             System.out.println("111111111111111111 Update with position parameter has been successful :"
                     + writeResult.toString());
         							}
         
     } catch (DataIntegrityViolationException die) {
         System.out.println("Update failed ====>" + die.getMessage());
         System.out.println("Trying to update without position parameters ...");

     			}

		return writeResult;
		}
	
	@Override
	 public WriteResult deleteAlertSourceById(String ids,String descA) {
		WriteResult writeResult = null;
		AlertSource als = new AlertSource();als.setIds(ids);
		 Query query = new Query();
         query.addCriteria(Criteria.where("descA").is(descA).and("alertsources.ids").is(ids) );
        
        
        try {

            System.out.println(query.toString());
            //System.out.println(update.toString());
            Update update = new Update().pull("alertsources", new BasicDBObject("ids", ids));
            writeResult = mongoTemplate.updateFirst(query, update, Alert.class);
            	//System.out.println(wc.getLastError());
        if (writeResult != null) {

            System.out.println("111111111111111111 Remove with position parameter has been successful :"
                    + writeResult.toString());
        							}
        
    } catch (DataIntegrityViolationException die) {
        System.out.println("Update failed ====>" + die.getMessage());
        System.out.println("Trying to Remove without position parameters ...");

    			}

		return writeResult;
		}
	
}
