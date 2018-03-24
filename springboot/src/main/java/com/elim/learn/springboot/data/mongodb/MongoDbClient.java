/**
 * 
 */
package com.elim.learn.springboot.data.mongodb;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.elim.learn.springboot.data.mongodb.entity.Person;
import com.mongodb.MongoClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Elim
 * 2018年3月24日
 */
@Slf4j
public class MongoDbClient {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "springdata");
        /*Person person = new Person();
        person.setId(1);
        person.setName("zhangsan");
        person.setAge(30);
        mongoTemplate.insert(person);*/
        Query query = new Query(Criteria.where("name").is("zhangsan"));
        Person person2 = mongoTemplate.findOne(query, Person.class);
        log.info("find user is {}", person2);
//        mongoTemplate.dropCollection("person");
    }
    
}
