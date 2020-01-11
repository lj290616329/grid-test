package com.grid.management.apps.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grid.management.apps.entity.Street;
import com.grid.management.apps.mapper.StreetMapper;
import com.grid.management.apps.service.StreetService;

import tk.mybatis.mapper.entity.Example;

@Service
public class StreetServiceImpl implements StreetService {
	
	@Autowired 
	StreetMapper streetMapper;
	
	@Override
	public Street getById(String id) {
		return streetMapper.selectByPrimaryKey(id);
	};
	
	
	@Override
	public int insert(Street street) {
		if(null==street.getCdt())
			street.setCdt(new Date());
		return  streetMapper.insert(street);
	};
	
	@Override
	public int delete(String id) {
		return streetMapper.deleteByPrimaryKey(id);
	};
	
	@Override
	public int update(Street street) {
		if(null==street.getCdt())
			street.setCdt(new Date());
		return streetMapper.updateByPrimaryKeySelective(street);
	};
	
	@Override
	public List<Street> findAll() {
		return streetMapper.selectAll();
	};
	
	@Override
	public List<Street> findByName(String name){
		Example example = new Example(Street.class);
		// 创建Criteria
		Example.Criteria criteria = example.createCriteria();
		// 添加条件
		if(StringUtils.isNotBlank(name)) {
			criteria.andLike("name", "%"+name+"%");
		}
		List<Street> list = streetMapper.selectByExample(example);
		return list;
	}
	
}
