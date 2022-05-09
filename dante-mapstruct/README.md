## MapStruct使用指南

### 一. 介绍

随着微服务和分布式应用程序迅速占领开发领域，数据完整性和安全性比以往任何时候都更加重要。在这些松散耦合的系统之间，安全的通信渠道和有限的数据传输是最重要的。大多数时候，终端用户或服务不需要访问模型中的全部数据，而只需要访问某些特定的部分。

数据传输对象(Data Transfer Objects, DTO)经常被用于这些应用中。DTO只是持有另一个对象中被请求的信息的对象。通常情况下，这些信息是有限的一部分。例如，在持久化层定义的实体和发往客户端的DTO之间经常会出现相互之间的转换。由于DTO是原始对象的反映，因此这些类之间的映射器在转换过程中扮演着关键角色。

这就是MapStruct解决的问题：手动创建bean映射器非常耗时。 但是该库可以自动生成Bean映射器类。





### 五. 参考资料

- https://juejin.cn/post/6956190395319451679
- https://www.cnblogs.com/pengdai/p/14541443.html
- https://www.baeldung.com/java-performance-mapping-frameworks
- https://mapstruct.org/

