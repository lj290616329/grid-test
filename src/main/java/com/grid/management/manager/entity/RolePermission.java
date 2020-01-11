package com.grid.management.manager.entity;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;
/**
 * 角色&&权限中间表
 * @author lj
 *
 */

@Data
@Table(name = "sys_role_permission")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;
	/**{@link Role.id}*/
    private String rid;
    /**{@link Permission.id}*/
    private String pid;
}