package com.elim.springboot.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Document
@Data
public class User {

    @Id
    @JSONField(name="user_id")
    private Long userId;
    private String name;
    @Field("user_name")
    private String username;
    
}
