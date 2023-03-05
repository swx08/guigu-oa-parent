package com.guigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName:ServiceOaApplication
 * Package:com.guigu
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/3 - 22:39
 * @Version:v1.0
 */
@SpringBootApplication//启动类
@EnableTransactionManagement//事务注解
public class ServiceOaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOaApplication.class,args);
    }
}
