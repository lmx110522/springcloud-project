# springcloud入门级学习项目
**关于springcloud的一站式服务治理框架的学习，里面包括 eureka、ribbon、config、feign...,但是往往配置的时候，都需要到处拷贝，所以
  索性也写一个案例，业务逻辑不太强，主要还是使用上**
## 第一个阶段
目前已经搭建好eureka集群和服务注册的集群
![springcloud配置第一阶段](http://qiniuyun.lmxljc.xyz/spring1.png "springcloud配置第一阶段")

## Ribbon轮序机制自定义
默认是轮询，有以下选择的方式
![IRule算法选择](http://qiniuyun.lmxljc.xyz/IRule%E6%9C%BA%E5%88%B6.png "IRule算法选择")
### 自定义实现ribbon算法
官方文档明确写出自定义rule类的实现不能放在@ComponentScan所扫描的当前包下以及子包下,否则我们自定义的这个配置类会被所有的Ribbon客户端所共享，也就是说，达不到特殊化定制的目的
#### 启动类配置如下代码
**MyCustmRule要定义在启动类包外面**
```java
@RibbonClient(name = "MICROSERVICECLOUD-DEPT",configuration = MyCustmRule.class)
```
模仿Random实现自定义每台机器执行5次再轮询执行
```java
package com;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import java.util.List;
import java.util.Random;

public class MyCustmRule extends AbstractLoadBalancerRule {

    private int total = 0; // 总共被调用更多次数

    private int currentIndex = 0; //当前提供服务的机器号

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while(server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();
                List<Server> allList = lb.getAllServers();
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }
                if(total < 5){
                    server = upList.get(currentIndex);
                    total++;
                }
                else{
                    total = 0;
                    currentIndex++;
                    if(currentIndex >= upList.size()){
                        currentIndex = 0;
                    }
                }



                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
        }
    }

    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}

```

### 配置feign(内含有负载均衡)
**一个声明式的WebService客户端**

在接口处记得使用 `@FeignClient(value = "MICROSERVICECLOUD-DEPT")`,
然后在启动类上面使用`@EnableFeignClients(value = "com.nyist.service")`

### 配置断路器Hystrix
