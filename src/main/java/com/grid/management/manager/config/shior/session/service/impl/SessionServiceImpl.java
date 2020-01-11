package com.grid.management.manager.config.shior.session.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grid.management.manager.config.shior.session.dao.CustomSessionDAO;
import com.grid.management.manager.config.shior.session.entity.UserOnline;
import com.grid.management.manager.config.shior.session.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService{
	
	private final CustomSessionDAO sessionDAO;
 
	@Autowired
    public SessionServiceImpl(CustomSessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }
	
	
    @Override
    public List<UserOnline> list() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                String username = (String)  principalCollection.getPrimaryPrincipal();
                userOnline.setUserName(username);
            }
            userOnline.setId((String) session.getId());
            userOnline.setHost(session.getHost());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            userOnline.setTimeout(session.getTimeout());
            list.add(userOnline);
        }
        return list;
    }
 
 
    @Override
    public boolean forceLogout(String sessionId) {
        Session session =null;
        
        try {
        	session = sessionDAO.readSession(sessionId);
		} catch (UnknownSessionException e) {
			// TODO: handle exception
			return true;
		}
        session.setTimeout(0);
        return true;
    }
}