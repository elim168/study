/**
 * 
 */
package com.elim.learn.springboot.data.mongodb.entity;

import java.math.BigInteger;

import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Elim
 * 2018年3月24日
 */
@Data
@Document
public class Person {

    private BigInteger id;
    
    private String name;
    
    private Integer age;
    
    @Version
    private Integer version;
    /**
     * 照片
     */
    private byte[] picture;
    
}
