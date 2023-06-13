
## 官方：http://neutrino-proxy.dromara.org/

## WEB端口：服务端API、后台管理访问端口，默认为：8888
## 服务端等待客户端连接的端口，非SSL：默认9000，SSL端口：默认9002（若不需要SSL支持，可不配置SSL端口）
## HTTP代理端口：默认80，用于域名映射，若无需域名映射，可以忽略。

## IP：103.163.47.16		port：9122

java -jar neutrino-proxy-client.jar config=app.yml

## nohup java -jar neutrino-proxy-client.jar config=app.yml > client.log 2>&1 &