package org.dante.springmvc.spring.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig {

	private Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

	@Value("${datasource.url}")
	private String dbUrl;

	@Value("${datasource.username}")
	private String username;

	@Value("${datasource.password}")
	private String password;

	@Value("${datasource.driver-class-name}")
	private String driverClassName;

	@Value("${datasource.initialSize}")
	private int initialSize;

	@Value("${datasource.minIdle}")
	private int minIdle;

	@Value("${datasource.maxActive}")
	private int maxActive;

	/**
	 * 配置获取连接等待超时的时间
	 */
	@Value("${datasource.maxWait}")
	private int maxWait;

	/**
	 * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	 */
	@Value("${datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	/**
	 * 配置一个连接在池中最小生存的时间，单位是毫秒
	 */
	@Value("${datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${datasource.validationQuery}")
	private String validationQuery;

	@Value("${datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${datasource.testOnReturn}")
	private boolean testOnReturn;

	/**
	 * 打开PSCache，并且指定每个连接上PSCache的大小
	 */
	@Value("${datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	/**
	 * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙, 'config'用于加密
	 */
	@Value("${datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${datasource.filters}")
	private String filters;

	/**
	 * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
	 */
	@Value("${datasource.connectionProperties}")
	private String connectionProperties;

	/**
	 * 合并多个DruidDataSource的监控数据
	 */
	@Value("${datasource.useGlobalDataSourceStat}")
	private boolean useGlobalDataSourceStat;

	/**
	 * @Primary表示在同样的DataSource中，首先使用被标注的DataSource,将覆盖其他来源的DataSource
	 * 
	 * @return DataSource
	 */
	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		// datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);

		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		// datasource.setConnectionProperties("config.decrypt=true;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAN2MkDG3rm/w74X/UHxLD687pqtaOcZj3eJx7eyFMzWFVG1FTeSqgvj3bzIWqxvxc0BxLTN2RJKtXslk4Gpl9x8CAwEAAQ==;druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000;");
		datasource.setConnectionProperties(connectionProperties);

		return datasource;
	}
	
	
	@Value("classpath:org/dante/springmvc/mybatis/mapper/*.xml")
	private Resource[] mapperLocation;

	@Value("classpath:org/dante/springmvc/mybatis/mybatis-config.xml")
	private Resource configLocation;
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setMapperLocations(mapperLocation);
		sqlSessionFactory.setConfigLocation(configLocation);
		return sqlSessionFactory;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		return transactionManager;
	}

}
