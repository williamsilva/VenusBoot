package com.alvorecer.venus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alvorecer.venus.model.City;

public interface Citys extends JpaRepository<City, Long> {
	
	public List<City> findByStateId(Long codeState);
}
