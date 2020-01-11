package com.grid.management.manager.config.shior.session.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grid.management.manager.config.shior.session.entity.UserOnline;
@Service
public interface SessionService {
    List<UserOnline> list(); //得到系统在线用户
 
    boolean forceLogout(String sessionId); //强制登出
}