package com.td.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
@Configuration(proxyBeanMethods = false)
public class HibernateConfig {

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

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.td.entity");

        Properties properties = new Properties();
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.FORMAT_SQL, true);
        // 不用配置
        //properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        factoryBean.setHibernateProperties(properties);

        return factoryBean;
    }

    // 使用spring声明式事务
    // 还需要使用@EnableTransactionManagement开启
    @Bean
    public HibernateTransactionManager transactionManager(LocalSessionFactoryBean localSessionFactoryBean) throws Exception {
        SessionFactory sessionFactory = localSessionFactoryBean.getObject();
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    // 使用JPA方式操作
    // 如果想要用@PersistenceContext来注入EntityManager
    // 则需要引入hibernate-jpa-2.1-api
    @Bean
    public EntityManager entityManager(LocalSessionFactoryBean localSessionFactoryBean) throws Exception {
        SessionFactory sessionFactory = localSessionFactoryBean.getObject();
        EntityManager entityManager = sessionFactory.createEntityManager();
        return entityManager;
    }
}
