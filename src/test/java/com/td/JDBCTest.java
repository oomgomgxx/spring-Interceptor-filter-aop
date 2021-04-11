package com.td;


import com.td.entity.User;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTest {

    private final static String MYSQL_PROPS_FILE_PATH = "jdbc.properties";
    private final static String MYSQL_PROP_URL_KEY = "mysql.url";
    private final static String MYSQL_PROP_USERNAME_KEY = "mysql.username";
    private final static String MYSQL_PROP_PASSWORD_KEY = "mysql.password";

    private Properties readProps() throws Exception {

        Properties properties = new Properties();

        // 加载类路径下的配置文件
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(MYSQL_PROPS_FILE_PATH);
        properties.load(new BufferedInputStream(inputStream));

        return properties;
    }

    // 注意：真是操作会使用反射来解决类型的不确定性
    @Test
    public void test() throws Exception {

        Properties props = readProps();
        String url = props.getProperty(MYSQL_PROP_URL_KEY);
        String name = props.getProperty(MYSQL_PROP_USERNAME_KEY);
        String password = props.getProperty(MYSQL_PROP_PASSWORD_KEY);

        Connection connection = DriverManager.getConnection(url, name, password);

        String sql = "insert into user(`name`, `age`) values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        User user = User.builder().name("sam").age(18).build();
        statement.setString(1, user.getName());
        statement.setInt(2, user.getAge());

        int updateRowNums = statement.executeUpdate();

        // 返回当前insert操作生成的自增主键
        ResultSet keysRS = statement.getGeneratedKeys();
        if (updateRowNums == 1) {
            keysRS.next();
            user.setId(keysRS.getInt(Statement.RETURN_GENERATED_KEYS));
        }

        System.out.println("当前插入的记录：" + user);
    }
}
