生产者：
	1、连接到RabbitMQ
	2、获取channel（信道）
	3、声明exchange（交换器）
	4、创建消息
	5、发布消息
	6、关闭channel（信道）
	7、关闭连接
	
消费者：
	1、连接到RabbitMQ
	2、获取channel（信道）
	3、声明exchange（交换器）
	4、声明queue（队列）
	5、绑定交换器和队列
	6、消费消息
	7、关闭channel（信道）
	8、关闭连接

死信队列：
	DLX, Dead-Letter-Exchange。利用DLX, 当消息在一个队列中变成死信（dead message）之后，它能被重新publish到另一个Exchange，这个Exchange就是DLX。消息变成死信一向有一下几种情况：
	1、消息被拒绝（basic.reject/ basic.nack）并且requeue=false
	2、消息TTL过期（Time-To-Live 过期时间）
	3、队列达到最大长度
	DLX也是一个正常的Exchange，和一般的Exchange没有区别，它能在任何的队列上被指定，实际上就是设置某个队列的属性，当这个队列中有死信时，RabbitMQ就会自动的将这个消息重新发布到设置的Exchange上去，进而被路由到另一个队列，可以监听这个队列中消息做相应的处理。
	核心代码实现：通过在queueDeclare方法中加入“x-dead-letter-exchange”实现。
		channel.exchangeDeclare("some.exchange.name", "direct");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-dead-letter-exchange", "some.exchange.name");
		channel.queueDeclare("myqueue", false, false, false, args);

你也可以为这个DLX指定routing key，如果没有特殊指定，则使用原队列的routing key
		args.put("x-dead-letter-routing-key", "some-routing-key");