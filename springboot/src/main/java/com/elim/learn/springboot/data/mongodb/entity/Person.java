/**
 * 
 */
package com.elim.learn.springboot.data.mongodb.entity;

import java.math.BigInteger;

import org.springframework.data.annotation.Version;

import lombok.Data;

/**
 * @author Elim
 * 2018年3月24日
 */
@Data
public class Person {

    private BigInteger id;
    
    private String name;
    
    private Integer age;
    
    @Version
    private int version;
    
}
