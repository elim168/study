/**
 * 
 */
package com.elim.learn.springboot.data.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.elim.learn.springboot.data.mongodb.entity.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Elim
 * 2018年3月24日
 */
@Service
@Slf4j
public class PersonService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PersonRepository personRepository;
    
//    @PostConstruct
    public void post() {
        Query query = new Query(Criteria.where("id").lt(3));
        Person person = this.mongoTemplate.findOne(query, Person.class);
        log.info("current db is : {}", this.mongoTemplate.getDb().getName());
        log.info("find person which id less than 3 is {}", person);
        
        /*person = new Person();
        person.setName("wangwu");
        person.setAge(30);
        this.mongoTemplate.insert(person);
        log.info("inserted person is : {}", person);
        for (int i=0; i<10; i++) {
            this.mongoTemplate.updateMulti(Query.query(Criteria.where("name").is("wangwu")), new Update().inc("age", 2), Person.class);
        }
        log.info("after update is : {}", this.mongoTemplate.findOne(Query.query(Criteria.where("name").is("wangwu")), Person.class));*/
        log.info("all person those age less than 32 is {}", this.personRepository.findAllByAges(32));
    }
    
}
