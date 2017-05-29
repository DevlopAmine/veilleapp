package com.start.daoservices;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.start.models.Instance;

public interface InstanceService {

	void saveInstance(Instance inst);
	void removeInstance(ObjectId idI);
	ObjectId findInstanceId(String descI);
	List<Instance> findInstancesByCustomerId(ObjectId oId);
}
