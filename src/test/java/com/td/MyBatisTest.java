package com.td;

import com.td.config.MyBatisConfig;
import com.td.entity.User;
import com.td.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MyBatisConfig.class})
public class MyBatisTest {

/**
    // 操作方式1：使用temple
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    // 在SpringJUnit4ClassRunner中，声明式事务中的单元测试是不会落盘的
    @Transactional
    @Test
    public void testSqlSessionTemplate() {
        User user = User.builder().id(1).name("sam").age(29).build();
        final UserMapper mapper = sessionTemplate.getMapper(UserMapper.class);
        mapper.insert(user);
    }
*/
    // 操作方式2：直接用mapper代理
    @Autowired
    // UserMapper没有显式用Spring住家加入容器，所以编译器会警告，但不用理
    // 因为是用@MapperScan加入容器的
    private UserMapper userMapper;

    @Test
    @Transactional
    public void testMapper() {
        User user = User.builder().name("lisa").age(18).build();
        userMapper.insert(user);
        System.out.println(user);
    }
}
