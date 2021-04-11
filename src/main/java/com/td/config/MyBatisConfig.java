package com.td.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
// 扫描Mapper接口，将Mapper接口封装为一个个MapperFactoryBean
// MapperFactoryBean#getObject实质就是session.getMapper(Interface.class)
// 即返回一个Mapper代理实例（MapperProxy，JDK动态代理）
// 代理参数过程可参考：MapperProxyFactory
@MapperScan(basePackages = "com.td.mapper")
@PropertySource("classpath:jdbc.properties")
@Configuration(proxyBeanMethods = false)
public class MyBatisConfig {

    @Value("${mysql.username}")
    private String username;
    @Value("${mysql.password}")
    private String password;
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.driver}")
    private String driverClass;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

//     如果接入spring声明式事务，则不是用JdbcTransactionFactory而是用SpringManagedTransactionFactory
//    @Bean
//    public TransactionFactory transactionFactory() {
//        JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
//        return transactionFactory;
//    }
    @Bean
    public TransactionFactory transactionFactory() {
        SpringManagedTransactionFactory transactionFactory = new SpringManagedTransactionFactory();
        return transactionFactory;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, TransactionFactory transactionFactory) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionFactory(transactionFactory);

        // 指定XML Mapper目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:mapper/*.xml");
        factoryBean.setMapperLocations(resources);

//        如果需要添加插件
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactoryBean sqlSessionFactoryBean)  {
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
//        return template;
//    }

    // 使用spring声明式事务
    // 还需要使用@EnableTransactionManagement开启
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
