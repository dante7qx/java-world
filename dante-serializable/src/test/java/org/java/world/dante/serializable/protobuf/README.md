## protobuf

### 一. 概述

Protocol buffers是一个灵活的、高效的、自动化的用于**对结构化数据进行序列化的协议**。将需要序列化的数据结构用一个 **.proto** 描述，再使用protobuf提供的生成工具生成源码，可跨语言，可以用于**网络通信**和**数据存储**。


### 二. 安装
#### 1. Mac
1) 下载 （https://github.com/protocolbuffers/protobuf/releases）**protobuf-all-<version>.tar.gz**

2）步骤

```shell
## 本例下载的版本 3.8.0
tar -zxf protobuf-all-3.8.0.tar.gz
cd protobuf-3.8.0
## 设置编译目录，/usr/local/protobuf 为自己配置的编译安装目录
./configure --prefix=/usr/local/protobuf
## 还在 protobuf-3.8.0/ 下执行（make 时间较久）
make			
sudo make install
## 配置环境变量
vi ~/.bash_profile
export PROTOBUF=/usr/local/protobuf
export PATH=$PROTOBUF/bin:$PATH#
source ~/.bash_profile
## 测试，输出 libprotoc 3.8.0 表示安装成功
protoc --version
```

### 三. 使用



```shell
cd /Users/dante/Documents/Project/dante-serializable/src/main/java/org/java/world/dante/serializable/protobuf

protoc -I=./ --java_out=/Users/dante/Desktop/ ./user.proto 
```



### 五. 参考资料

- https://github.com/protocolbuffers/protobuf
- https://developers.google.com/protocol-buffers