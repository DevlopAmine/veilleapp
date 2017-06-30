package com.start.daoservices;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.social.twitter.api.Tweet;

import com.mongodb.WriteResult;
import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.SNresult;

public interface AlertService {


	void deleteAlert(String alertId);
	List<Alert> findAlertsByInstanceId(ObjectId oId);
	boolean issetAlert(String desc);
	String saveAlert(Alert alert, String descI);
	Alert getAlert(String desc);
	Alert findAlertByDesc(String descA);
	String persistAlert(Alert alert, List<AlertSource> list, String descI);
	List<SNresult> filterData(List<Tweet> tweetList);
	WriteResult UpdateAlertSourceById(AlertSource als, String descA);
	WriteResult deleteAlertSourceById(String ids, String descA);
	
}
