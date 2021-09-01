package com.healthcare.system.domain;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface ServiceRepository extends JpaRepository<HealthCareServices, Integer> {
 
}