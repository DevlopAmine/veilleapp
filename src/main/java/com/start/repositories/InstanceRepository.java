package com.start.repositories;
import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.start.models.Instance;


public interface InstanceRepository extends CrudRepository<Instance, String> {
	public List<Instance> findAll();
	public Instance findOne(String id);
	public Instance save(Instance Instance);
	public Boolean save(List<Instance> listI);
	
	public void delete(Instance Instance);
}



	

	

