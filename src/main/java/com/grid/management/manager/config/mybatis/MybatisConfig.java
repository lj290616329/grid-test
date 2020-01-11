package com.grid.management.manager.config.mybatis;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class MybatisConfig {
	@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.grid.management.*.mapper");
        System.out.println(21321);
        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("notEmpty", "true");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("mappers", "com.grid.management.manager.config.mybatis.CustomerMapper");
        properties.setProperty("IDENTITY", "SELECT REPLACE(UUID(),''-'','''')");//使用UUID作為主鍵
        properties.setProperty("ORDER","BEFORE");//将查询主键作为前置操作

        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}

