package com.nyist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudDemoEureka7003 {
    public static void main(String[] args)
    {
        SpringApplication.run(SpringCloudDemoEureka7003.class, args);
    }
}
