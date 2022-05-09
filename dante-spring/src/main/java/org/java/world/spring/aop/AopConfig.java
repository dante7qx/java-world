package org.java.world.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("org.java.world.spring.aop")
@EnableAspectJAutoProxy
public class AopConfig {

}
