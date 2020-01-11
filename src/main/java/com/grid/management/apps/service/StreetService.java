package com.grid.management.apps.service;

import java.util.List;

import com.grid.management.apps.entity.Street;

public interface StreetService {
	Street getById(String id);
	int insert(Street street);
	int delete(String id);
	int update(Street street);
	List<Street> findAll();
	List<Street> findByName(String name);
}
