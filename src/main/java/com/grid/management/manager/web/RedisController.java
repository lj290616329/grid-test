package com.grid.management.manager.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grid.management.manager.config.redis.RedisUtil;
import com.grid.management.manager.entity.Admin;
import com.grid.management.manager.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private AdminService adminService;


    @CachePut("cache1")
    @RequestMapping("set")
    public boolean redisset(String key, String value){
    	Admin admin = new Admin();
    	admin.setName(key);
    	admin.setPassword(value);
    	adminService.insert(admin);
    	
    	log.error("缓存");
    	System.out.println(new Date().getTime());
        return redisUtil.set(key,value);
    }
    @Cacheable("cache2")
    @RequestMapping("get")
    public Object redisget(String key){
    	log.error("获取缓存");
    	System.out.println(new Date().getTime());
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}