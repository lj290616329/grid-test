package com.grid.management.manager.config.shior.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheManager;

import com.grid.management.manager.config.shior.RetryLimitHashedCredentialsMatcher;
import com.grid.management.manager.config.shior.UserRealm;
import com.grid.management.manager.config.shior.filter.KickoutSessionControlFilter;
import com.grid.management.manager.config.shior.listenter.CustomSessionListener;
import com.grid.management.manager.config.shior.session.dao.CustomSessionDAO;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import javax.servlet.Filter;

@Slf4j
@Configuration
public class ShiorConfig {
	
	/**
     * @see DefaultWebSessionManager
     * @return
     */
    
    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setCacheManager(getEhCacheManager());
        //单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时 默认为
        //sessionManager.setSessionValidationInterval(30000);//定时查询所有session是否过期的时间
        //去除url的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(60000);//设置session过期时间
        sessionManager.setSessionDAO(sessionDAO());//设置SessionDao
        
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new CustomSessionListener());
        sessionManager.setSessionListeners(listeners);
        
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
    
    
    
    @Bean(name="sessionIdGenerator")
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
    
    
    @Bean
    public SessionDAO sessionDAO() {
    	return new CustomSessionDAO();//使用默认的MemorySessionDAO
    }
    
    @Bean(name="sessionDAO")
    public SessionDAO sessionDAO1() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //使用ehCacheManager
        enterpriseCacheSessionDAO.setCacheManager(getEhCacheManager());
        //设置session缓存的名字 默认为 eplusShiroCache
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("eplusShiroCache");
        //sessionId生成器
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }
    
    
	
	@Bean(name="ehCacheManager")
    public EhCacheManager getEhCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		CacheManager cacheManager2 = new CacheManager();
		cacheManager2.setName("eplusShiroCache");
		/**
		 * String name,int maxElementsInMemory,boolean overflowToDisk,boolean eternal,long timeToLiveSeconds,
         long timeToIdleSeconds,boolean diskPersistent,long diskExpiryThreadIntervalSeconds
		 */
		Cache cache = new Cache("eplusShiroCache", 10000,false,false,120, 120, true, 120);
		Cache cache2 = new Cache("passwordRetryCache", 2000,false,false,0, 60, true, 2);
		cacheManager2.addCache(cache);
		cacheManager2.addCache(cache2);
		cacheManager.setCacheManager(cacheManager2);
		/**
		 * 以上配置等同于以下注释配置
		 */
		//cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		
		return cacheManager;
	}
	
	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean(name="shiroFilterFactoryBean")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		log.info("getShiroFilterFactoryBean");
		// TODO Auto-generated method stub
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		
		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        filtersMap.put("kickout", kickoutSessionControlFilter());
        factoryBean.setFilters(filtersMap);
		/**
		 * 设置url 过滤原则
		 * anon:无需认证就可以访问
		 * authc:必须认证才可以访问
		 * user:如果设置了rememberMe 的功能可以访问
		 * perms:必须有资源权限授权才可以访问
		 * role:必须有角色权限授权才可以访问
		 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		filterMap.put("/api/**", "authc");
		filterMap.put("/logout", "logout");
		factoryBean.setLoginUrl("/login");
		factoryBean.setUnauthorizedUrl("/fail");
		factoryBean.setSuccessUrl("/index");
		factoryBean.setFilterChainDefinitionMap(filterMap);
		return factoryBean;
	}
	
	
	@Bean(name="kickoutSession")
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(getEhCacheManager());
        kickoutSessionControlFilter.setSessionManager(defaultWebSessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/kickout");
        return kickoutSessionControlFilter;
    }
	
	/**
	 * 创建DefaultWebSecurityManager
	 * @param userRealm
	 * @return
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		System.out.println("securityManager");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		
		securityManager.setCacheManager(getEhCacheManager());//这个如果执行多次，也是同样的一个对象;
        securityManager.setSessionManager(defaultWebSessionManager());
		
		return securityManager;
	}
	
	@Bean("credentialsMatcher")
	public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(){
		RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(getEhCacheManager());
		retryLimitHashedCredentialsMatcher.setLimitCount(5);
		return retryLimitHashedCredentialsMatcher;
	}
	
	/**
	 * 创建realm
	 * @return
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		UserRealm shiroRealm = new UserRealm();
		System.out.println("no come here");
		shiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
		return shiroRealm;
	}
	
	
	/**
     * 注入LifecycleBeanPostProcessor
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        System.out.println("ShiroConfiguration.lifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }

    @ConditionalOnMissingBean
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        System.out.println("ShiroConfiguration.getDefaultAdvisorAutoProxyCreator()");
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
	
}
