package com.nyist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class DeptConsumer80 {
    public static void main(String[] args)
    {
        SpringApplication.run(DeptConsumer80.class, args);
    }
}
