package com.elim.learn.springboot.spring.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.Application;
import com.elim.springboot.core.jdbc.JdbcService;


@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class JdbcTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcService jdbcService;
    
    @Test
    public void testDataSource() throws Exception {
        Connection connection = this.dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select name from tb_user");
        List<String> names = new ArrayList<>();
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        System.out.println(names);
        System.out.println(this.dataSource);
    }
    
    @Test
    public void testJdbcService() throws Exception {
        List<String> users = this.jdbcService.getUsers();
        System.out.println(users);
    }
    
}
