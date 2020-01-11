package com.grid.management.manager.config.shior.session.entity;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 在线用户信息
 * @author lj
 *
 */
@Data
public class UserOnline implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String host;
	private Date startTimestamp;
	private Date lastAccessTime;
	private Long timeout;

}
