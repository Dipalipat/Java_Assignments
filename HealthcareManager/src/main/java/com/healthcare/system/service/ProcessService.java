package com.healthcare.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.healthcare.system.domain.HealthCareServices;
import com.healthcare.system.domain.ServiceRepository;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProcessService {

    @Autowired
    private ServiceRepository repo;


    public List<HealthCareServices> listAll() throws DataAccessException{
        return repo.findAll();
    }

    
    public void addNewService(HealthCareServices healthCareServices)throws DataAccessException {
		healthCareServices.setCreated_time(new Date());
		healthCareServices.setLast_updated(new Date());
        repo.save(healthCareServices);
    }
   
    public void updateService(HealthCareServices healthCareServices) throws DataAccessException{
    	
    	HealthCareServices existServices = get(healthCareServices.getId());
		existServices.setService_name(healthCareServices.getService_name());
		existServices.setResponse(healthCareServices.getResponse());
		existServices.setUrl(healthCareServices.getUrl());
		existServices.setLast_updated(new Date());
        repo.save(existServices);
    }
    
    public void save(HealthCareServices healthCareServices)throws DataAccessException {
        repo.save(healthCareServices);
    }

    public HealthCareServices get(Integer id) throws DataAccessException {
        return repo.findById(id).get();
    }

    public void delete(Integer id)throws DataAccessException {
        repo.deleteById(id);
    }
}
