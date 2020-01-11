package com.grid.management.manager.mapper;

import org.springframework.stereotype.Repository;

import com.grid.management.manager.config.mybatis.CustomerMapper;
import com.grid.management.manager.entity.AdminRole;

@Repository
public interface AdminRoleMapper extends CustomerMapper<AdminRole>{
	
}
