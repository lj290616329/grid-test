package com.grid.management.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


/**
 * 用户账号表
 * @author lj
 *
 */
@Data
@Table(name = "sys_admin")
public class Admin implements Serializable{  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//0:禁止登录
	public static final Short UNVALID = -1;
	//1:有效
	public static final Short VALID = 0;
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	/**名称*/
    private String name;
    /**邮箱 | 登录帐号*/
    private String loginName;
    /**密码*/
    private transient String password;
    /**明文密码 新增时使用*/
    @Transient
    private String plainPassword;
    /**盐*/
    private String salt;
    /**创建时间*/
    private Date cdt;
    /**最后登录时间*/
    private Date updateTime;
    /**1:有效，0:禁止登录*/
    private Short status= VALID;
}
