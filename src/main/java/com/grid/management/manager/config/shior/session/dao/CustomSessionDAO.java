package com.grid.management.manager.config.shior.session.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomSessionDAO extends AbstractSessionDAO {
    
	private ConcurrentMap<Serializable, Session> sessions = new ConcurrentHashMap<Serializable, Session>();
 
    public CustomSessionDAO() {
    	
    }
 
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.storeSession(sessionId, session);
        return sessionId;
    }
 
    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        } else {
            return (Session)this.sessions.putIfAbsent(id, session);
        }
    }
 
    protected Session doReadSession(Serializable sessionId) {
        return (Session)this.sessions.get(sessionId);
    }
 
    public void update(Session session) throws UnknownSessionException{
		this.storeSession(session.getId(), session);
    }
 
    public void delete(Session session) {
        log.info("come delete session");
    	if (session == null) {
            //throw new NullPointerException("session argument cannot be null.");
        } else {
            Serializable id = session.getId();
            if (id != null) {
				this.sessions.remove(id);
            }
        }
    }
 
    @SuppressWarnings("unchecked")
	public Collection<Session> getActiveSessions() {
        Collection<Session> values = this.sessions.values();
        return (Collection<Session>)(CollectionUtils.isEmpty(values) ? Collections.emptySet() : Collections.unmodifiableCollection(values));
    }
}