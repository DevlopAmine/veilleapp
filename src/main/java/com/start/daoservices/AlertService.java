package com.start.daoservices;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.social.twitter.api.Tweet;

import com.start.models.Alert;
import com.start.models.AlertSource;
import com.start.models.SNresult;

public interface AlertService {


	void deleteAlert(String alertId);
	List<Alert> findAlertsByInstanceId(ObjectId oId);
	boolean issetAlert(String desc);
	void saveAlert(Alert alert, String descI);
	Alert getAlert(String desc);
	Alert findAlertByDesc(String descA);
	void persistAlert(Alert alert, List<AlertSource> list, String descI);
	List<SNresult> filterData(List<Tweet> tweetList);
	boolean UpdateAlertSourceById(AlertSource als);
}
