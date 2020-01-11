package com.grid.management.manager.mapper;

import org.springframework.stereotype.Repository;

import com.grid.management.manager.config.mybatis.CustomerMapper;
import com.grid.management.manager.entity.Admin;

@Repository
public interface AdminMapper extends CustomerMapper<Admin>{
	void deleteById(String id);
}
