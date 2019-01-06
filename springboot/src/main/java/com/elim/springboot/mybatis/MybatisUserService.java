package com.elim.springboot.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class MybatisUserService {

    @Autowired
    private UserMapper userMapper;
    
    public User findByUsername(String username) {
        return this.userMapper.findByUsername(username);
    }
    
    public User findById(Long id) {
        return this.userMapper.findById(id);
    }
    
    public List<User> findAll(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = this.userMapper.findAll();
        return users;
    }
    
}
