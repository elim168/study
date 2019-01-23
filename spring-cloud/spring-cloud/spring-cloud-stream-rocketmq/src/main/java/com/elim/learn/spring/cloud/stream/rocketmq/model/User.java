package com.elim.learn.spring.cloud.stream.rocketmq.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class User {

    @JSONField(name = "userId")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();
    @JSONField(name = "username")
    private String name;

}
