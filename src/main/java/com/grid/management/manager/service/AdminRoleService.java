package com.grid.management.manager.service;

import java.util.List;

import com.grid.management.manager.entity.AdminRole;

public interface AdminRoleService {
	
	void insert(AdminRole adminRole);
	
	void deleteAdminId(String aid);
	
	void deleteRoleId(String rid);
	
	List<AdminRole> getRoleList(String aid);
}

