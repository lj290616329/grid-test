package com.grid.management.manager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户角色表
 * @author lj
 *
 */
@Data
@Entity
@Table(name="sys_admin_role")
public class AdminRole implements Serializable{  
	private static final long serialVersionUID = 1L;
    private String aid;
    private String rid;
}