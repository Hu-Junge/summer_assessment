package com.hu.choutuan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//springboot启动类
@Slf4j //便于记录日志
@SpringBootApplication
@ServletComponentScan//扫描webfilter等注解，创建过滤器
@EnableTransactionManagement//开启事务控制
public class ChoutuanApplication {
    public static void main(String[] args){
        SpringApplication.run(ChoutuanApplication.class,args);
        log.info("启动正常");
    }
}
