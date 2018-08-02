package com.elim.springboot.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from tb_user where username=#{username}")
    User findByUsername(String username);
    
    User findById(Long id);
    
    @Select("select * from tb_user order by id")
    List<User> findAll();
    
}
