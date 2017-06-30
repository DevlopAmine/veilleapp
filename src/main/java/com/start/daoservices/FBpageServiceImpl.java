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
import com.start.services.FBService;


public class FBpageServiceImpl  implements FBpageService{

	@Autowired
	FBpageRepository fbPageRepo;
	@Autowired 
	MongoTemplate mongoTemplate;
	@Autowired
	private FBService fbService;
	
	@Override
	public void savePage(Alert alert) {
		
		List<FbPage> fbList = new ArrayList<FbPage>(); 
		 
		List<Page> fbL = fbService.pageIdscollect(alert.getDescA());	
		int i=0;
		outerloop:
		for (Page page : fbL) {
			
			System.out.println("Id: "+page.getId()+"Name: "+page.getName());
			fbList.add(new FbPage(page.getId(),page.getName()));
			if(i==10)
				break outerloop;
			i++;
		}
	  
	   		
		System.out.println("nb d Ids"+fbL.size());
		
		for (FbPage fpage : fbList) {
			i++;
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
	public List<FbPage> getPages() {
		
		
		List<FbPage> pages =  (List<FbPage>) fbPageRepo.findAll();
		
		return pages;
		
	}
	@Override
	public String getPageId(String pageName)
	{
		FbPage page= mongoTemplate.findOne(query(where("pageName").is(pageName)),FbPage.class,"fbPage");
		return page.getPageId();
	}
	
}
