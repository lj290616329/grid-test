package com.grid.management.manager.mapper;

import org.springframework.stereotype.Repository;

import com.grid.management.manager.config.mybatis.CustomerMapper;
import com.grid.management.manager.entity.Role;

@Repository
public interface RoleMapper extends CustomerMapper<Role>{
	
}
