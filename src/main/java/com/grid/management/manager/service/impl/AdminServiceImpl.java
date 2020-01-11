package com.grid.management.manager.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grid.management.apps.entity.Street;
import com.grid.management.manager.entity.Admin;
import com.grid.management.manager.mapper.AdminMapper;
import com.grid.management.manager.service.AdminService;

import tk.mybatis.mapper.entity.Example;

@Service
public class AdminServiceImpl  implements AdminService {

	
	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public Admin getById(String id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(Admin admin) {
		adminMapper.insert(admin);
	}

	@Override
	public void delete(String id) {
		adminMapper.deleteById(id);
	}

	@Override
	public void update(Admin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public List<Admin> findAll() {
		// TODO Auto-generated method stub
		return adminMapper.selectAll();
	}

	@Override
	public List<Admin> findByName(String name) {
		Example example = new Example(Street.class);
		// 创建Criteria
		Example.Criteria criteria = example.createCriteria();
		// 添加条件
		if(StringUtils.isNotBlank(name)) {
			criteria.andLike("name", "%"+name+"%");
		}
		List<Admin> list = adminMapper.selectByExample(example);
		return list;
	}

}
