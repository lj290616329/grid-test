package com.grid.management.manager.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 角色表
 * @author lj
 *
 */
@Data
@Table(name = "sys_role")
@EqualsAndHashCode(callSuper=true)
public class Role extends IdEntity implements Serializable{
	private static final long serialVersionUID = 1L;
    /**角色名称*/
    private String name;
    /**角色类型*/
    private String type;
    //***做 role --> permission 一对多处理
    @Transient
    private List<Permission> permissions = new LinkedList<Permission>();
    
}