package com.start.services;

import java.util.List;
import com.google.api.services.customsearch.model.Result;
public interface GoogleService {

	
	public List<Result> searchedResults(String param);
}
