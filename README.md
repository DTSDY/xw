### Learning
#### 模块
```
* eureka: 服务发现、 注册
* redis: redis缓存
* base: 公用基础
* rabbitMQ: 队列消息发送
```

#### 使用
```
Spring Cloud,Spring Boot,Eureka,Redis,RabbitMQ,AMQP,ElasticSearch,Kibana,雪花算法
(待)Spring JPA,
```

#### 搭建
```
redis
* 解压 
* make install && make 
* 编辑 redis.conf
* cd src 
* redis-server ../redis.conf
  
```

```
rabbitMQ
注意 erlang 和 rabbitmq-server的版本依赖关系
rpm 安装 erlang rabbitmq

// 启动
rabbitmqctl rabbitmq-server start

// 图形化界面 端口 15672 
rabbitmq-plugins enable rabbitmq_management
// 添加一个用户
rabbitmqctl add_user ssssyy  123456
rabbitmqctl set_user_tags ssssyy administrator
rabbitmqctl set_permissions -p / ssssyy ".*" ".*" ".*"
```

```
elasticsearch + kibana
rpm 安装  etc/elasticsearch etc/kibana  var/log/es  var/log/kibana
es.yml编辑
* jvm.options       -> -Xms128m , -Xmx128m
* elasticsearch.yml -> network.host: 0.0.0.0 , cluster.initial_master_nodes: ["ssssyy"]
kibana.yml
* server.host: "0.0.0.0"
* elasticsearch.hosts: ["http://localhost:9000"]  # es端口默认9200->9000
* i18n.locale: "zh-CN" #语言
```
