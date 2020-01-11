package com.grid.management.apps.mapper;

import org.springframework.stereotype.Repository;

import com.grid.management.apps.entity.Street;
import com.grid.management.manager.config.mybatis.CustomerMapper;

@Repository
public interface StreetMapper extends CustomerMapper<Street>{
	/*String delete(String id);
	String update(Street street);
	Street findAll();*/
}
