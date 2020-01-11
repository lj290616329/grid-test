package com.grid.management.apps.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
/**
 * 街道
 * @author lijing
 *
 */
@Data
@Table(name = "app_street")
public class Street{
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;// 街道名称
	private String boundary;//边界点
	private String center;//中心点
	private String memo;// 街道信息描述
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cdt;//新增时间
}
