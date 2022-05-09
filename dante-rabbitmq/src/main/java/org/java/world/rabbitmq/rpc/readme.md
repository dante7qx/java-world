1、 When the Client starts up, it creates an anonymous exclusive callback queue.
2、 For an RPC request, the Client sends a message with two properties: replyTo, which is set to the callback queue and correlationId, which is set to a unique value for every request.
3、 The request is sent to an rpc_queue queue.
4、 The RPC worker (aka: server) is waiting for requests on that queue. When a request appears, it does the job and sends a message with the result back to the Client, using the queue from the replyTo field.
5、 The client waits for data on the callback queue. When a message appears, it checks the correlationId property. If it matches the value from the request it returns the response to the application.

1、当客户端启动时，它创建一个匿名独占回调队列。
2、对于RPC请求，客户端发送具有两个属性的消息：replyTo，设置为回调队列和correlationId，为每个请求设置唯一值。
3、请求被发送到rpc_queue队列。
4、RPC工作程序（又名：服务器）正在等待该队列上的请求。 当出现请求时，它执行作业，并使用replyTo字段中的队列将带有结果的消息发送回客户端。
5、客户端等待回调队列上的数据。 当出现消息时，它将检查correlationId属性。 如果它匹配来自请求的值，则它将响应返回给应用程序。