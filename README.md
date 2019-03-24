# springcloud入门级学习项目
**关于springcloud的一站式服务治理框架的学习，里面包括 eureka、ribbon、config、feign...,但是往往配置的时候，都需要到处拷贝，所以
  索性也写一个案例，业务逻辑不太强，主要还是使用上**
## 第一个阶段
目前已经搭建好eureka集群和服务注册的集群
![springcloud配置第一阶段](http://qiniuyun.lmxljc.xyz/spring1.png "springcloud配置第一阶段")

## Ribbon轮序机制自定义
默认是轮询，有以下选择的方式
![IRule算法选择](http://qiniuyun.lmxljc.xyz/IRule%E8%BD%AE%E8%AF%A2%E6%9C%BA%E5%88%B6.png "IRule算法选择")
