package com.elim.springboot.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document
@Data
public class User {

    @Id
    private Long userId;
    private String name;
    @Field("user_name")
    private String username;
    
}
