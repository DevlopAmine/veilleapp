package com.start.daoservices;

import java.util.List;
import com.start.models.Alert;
import com.start.models.FbPage;

public interface FBpageService {

	
	void savePage(Alert alert);
	List<FbPage> getPagesByParent(String parent);
	String getPageId(String pageName);
	
}
