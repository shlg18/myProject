package com.gh.store;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching //启动缓存
@MapperScan("com.gh.store.mapper") //MapperScan注解指定当前项目中的Mapper接口路径的位置

public class StoreApplication {

    private static Logger logger=LoggerFactory.getLogger(StoreApplication.class);
    public static void main(String[] args) {

        logger.info("项目正在启动~");
        SpringApplication.run(StoreApplication.class, args);

    }

}
