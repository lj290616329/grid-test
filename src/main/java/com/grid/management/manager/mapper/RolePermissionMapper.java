package com.grid.management.manager.mapper;

import org.springframework.stereotype.Repository;

import com.grid.management.manager.config.mybatis.CustomerMapper;
import com.grid.management.manager.entity.RolePermission;

@Repository
public interface RolePermissionMapper extends CustomerMapper<RolePermission>{
	
}
