package com.start.daoservices;

import java.util.List;

import org.bson.types.ObjectId;

import com.start.models.Alert;
import com.start.models.AlertSource;

public interface AlertService {

//	void saveTwAlert(Alert alert);	
//	void saveFBAlert(Alert alert);
//	void saveGgAlert(Alert alert);
	void deleteAlert(String alertId);
	List<Alert> findAlertsByInstanceId(ObjectId oId);
	boolean issetAlert(String desc);
	//ObjectId findAlertId(String descA);
	void saveAlert(Alert alert, String descI);
	Alert getAlert(String desc);
	Alert findAlertByDesc(String descA);
	void persistAlert(Alert alert, List<AlertSource> list, String descI);
}
