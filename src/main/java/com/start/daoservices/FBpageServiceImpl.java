package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.restfb.types.Page;
import com.start.models.Alert;
import com.start.models.FbPage;
import com.start.repositories.FBpageRepository;


public class FBpageServiceImpl  implements FBpageService{

	@Autowired
	FBpageRepository fbPageRepo;
	@Autowired 
	MongoTemplate mongoTemplate;
	@Override
	public void savePage(Alert alert) {
		//FbPage p = new FbPage();
		RestTemplate restTemplate = new RestTemplate();
		List<FbPage> fbList = new ArrayList<FbPage>(); 
	    ResponseEntity<List<Page>> FbResponse =
			        restTemplate.exchange("http://localhost:8080/svc/v1/fb/idscollect",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Page>>() {
			            });
		List<Page> fbL = FbResponse.getBody();		
		//p.setId(new ObjectId(FBpageMap.get("_id").toString()));
		for (Page page : fbL) {
			System.out.println("Id: "+page.getId()+"Name: "+page.getName());
			fbList.add(new FbPage(page.getId(),page.getName()));    
		}
		System.out.println("nb d Ids"+fbL.size());
		
		//p.setPageName(FBpageMap.get("pageName").toString());
		//p.setPageId(FBpageMap.get("pageId").toString());
		for (FbPage fpage : fbList) {
			fpage.setAlertid(alert.getId());
			fpage.setParentAlert(alert.getDescA());
			
		}
		
		fbPageRepo.save(fbList);
		
		
	}

	@Override
	public List<FbPage> getPagesByParent(String parent) {
		List<FbPage> pages =  mongoTemplate.find(query(where("parent").is(parent)),FbPage.class,"fbPage");
		for (FbPage f : pages) {
			System.out.println(f.getPageName() +"  "+f.getPageId());
			
		}
		return pages;
		
	}

	@Override
	public String getPageId(String pageName)
	{
		FbPage page= mongoTemplate.findOne(query(where("pageName").is(pageName)),FbPage.class,"fbPage");
		return page.getPageId();
	}
	
}
