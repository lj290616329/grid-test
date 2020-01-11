package com.grid.management.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grid.management.manager.entity.AdminRole;
import com.grid.management.manager.mapper.AdminRoleMapper;
import com.grid.management.manager.service.AdminRoleService;

import tk.mybatis.mapper.entity.Example;

@Service
public class AdminRoleServiceImpl  implements AdminRoleService {
	
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Override
	public void insert(AdminRole adminRole) {
		adminRoleMapper.insert(adminRole);
	}

	@Override
	public void deleteAdminId(String id) {
		Example example = new Example(AdminRole.class);
        example.createCriteria().andCondition("aid =", id);
        adminRoleMapper.deleteByExample(example);
	}

	@Override
	public void deleteRoleId(String id) {
		Example example = new Example(AdminRole.class);
        example.createCriteria().andCondition("rid =", id);
        adminRoleMapper.deleteByExample(example);
	}

	@Override
	public List<AdminRole> getRoleList(String  aid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
