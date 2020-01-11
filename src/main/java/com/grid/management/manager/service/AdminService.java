package com.grid.management.manager.service;

import java.util.List;

import com.grid.management.manager.entity.Admin;


public interface AdminService {
	Admin getById(String id);
	void insert(Admin admin);
	void delete(String id);
	void update(Admin admin);
	List<Admin> findAll();
	List<Admin> findByName(String name);
}
