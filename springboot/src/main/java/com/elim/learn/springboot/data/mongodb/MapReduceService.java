package com.elim.learn.springboot.data.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

/**
 * 测试Map Reduce
 * @author Elim
 * 2018年3月27日
 */
@Service
@Slf4j
public class MapReduceService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
//    @PostConstruct
    public void post() {
        MapReduceResults<ValueObject> results = this.mongoTemplate.mapReduce("jmr1", "classpath:mongo/map.js", "classpath:mongo/reduce.js", ValueObject.class);
        for (ValueObject value : results) {
            log.info("value is {}", value);
        }
    }
    
    @Data
    public static class ValueObject {
        private String id;
        private Integer value;
    }
    
}
