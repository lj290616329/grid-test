package com.grid.management.manager.entity;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 * @author lj
 *
 */
@Data
@Table(name = "sys_permission")
@EqualsAndHashCode(callSuper=true)
public class Permission extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pid;
	private String url;
	private String name;
}