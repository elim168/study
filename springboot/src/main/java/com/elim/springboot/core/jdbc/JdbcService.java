package com.elim.springboot.core.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<String> getUsers() throws Exception {
        this.jdbcTemplate.execute("create table tb_user(name varchar(100))");
        String sql = "select name from tb_user";
        List<String> names = this.jdbcTemplate.queryForList(sql, String.class);
        return names;
    }
    
}
