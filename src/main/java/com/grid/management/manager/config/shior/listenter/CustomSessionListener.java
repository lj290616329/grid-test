package com.grid.management.manager.config.shior.listenter;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grid.management.manager.config.shior.session.dao.CustomSessionDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomSessionListener implements SessionListener {

	@Autowired
    private CustomSessionDAO customSessionDAO;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
    	System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        try {
			customSessionDAO.delete(session);
		} catch (Exception e) {
			//主动删除后,shior 监听未进行删除时,会报错
			log.error("删除出错了");
		}
    }

    

}

