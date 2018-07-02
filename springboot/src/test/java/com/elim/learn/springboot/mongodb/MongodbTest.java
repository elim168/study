/**
 * 
 */
package com.elim.learn.springboot.mongodb;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.springboot.Application;
import com.elim.learn.springboot.data.mongodb.entity.Person;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

import reactor.core.publisher.Mono;

/**
 * 验证mongo
 * 
 * @author Elim 2018年4月15日
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongodbTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void testScript() {
        ScriptOperations scriptOps = this.mongoTemplate.scriptOps();
        ExecutableMongoScript script = new ExecutableMongoScript("function a(str) {return str;}");
        Object result = scriptOps.execute(script, "Hello World");
        System.out.println(result);
    }

    @Test
    public void testGroup() {
        //reduceFunction的第一个参数是当前的document，第二个参数是已经聚集的document聚集之后的结果
        GroupByResults<Object> group = this.mongoTemplate.group("person", GroupBy.key("age").initialDocument("{count:0,aa:0}")
                .reduceFunction("function(doc,prev){prev.count+=1;prev.aa+=doc.age}"), Object.class);
        System.out.println(group);
        System.out.println(group.getRawResults());
    }
    
    @Test
    public void testAggregate() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("age").count().as("count"), Aggregation.project("count").and("age").previousOperation());
        AggregationResults<Object> results = this.mongoTemplate.aggregate(aggregation, Person.class, Object.class);
        System.out.println(results);
    }

    @Test
    public void testIndex() {
        //索引不存在则创建索引
        String indexName = this.mongoTemplate.indexOps(Person.class).ensureIndex(new Index("age", Direction.ASC));
        System.out.println("indexName: " + indexName);
        //获取索引信息
        this.mongoTemplate.indexOps(Person.class).getIndexInfo().forEach(indexInfo -> {
            System.out.println(indexInfo.getName() + "--"  + indexInfo.getIndexFields() + "--" + indexInfo.isUnique());
        });
        //删除索引
        this.mongoTemplate.indexOps(Person.class).dropIndex(indexName);
    }
    
    @Test
    public void testInsertFile() {
        Person person = new Person(); 
        person.setId(new BigInteger("107"));
        person.setName("Picture108");
        //version不能设置初始值
//        person.setVersion(1);
        person.setAge(30);
        person.setPicture(new byte[1024 * 10]);
        this.mongoTemplate.save(person);
        
        List<Person> persons = this.mongoTemplate.findAllAndRemove(Query.query(Criteria.where("_id").in("107", "108")), Person.class);
        System.out.println(persons);
    }
    
    @Test
    public void testInsertFile2() throws Exception {
        String path = "/home/elim/jmeter.log";
        try (InputStream content = new FileInputStream(path)) {
            Document metadata = new Document();
//            metadata.put("key1", "value1");
//            this.gridFsTemplate.store(content, "jmeter.log", metadata);
        }
    }
    
    @Test
    public void testFindFile() throws Exception {
        GridFsResource resource = this.gridFsTemplate.getResource("jmeter.log");
        Assert.assertTrue("jmeter.log".equals(resource.getFilename()));
        Query query = Query.query(Criteria.where("filename").is("jmeter.log"));
        GridFSFindIterable gridFSFindIterable = this.gridFsTemplate.find(query);
        for (GridFSFile file : gridFSFindIterable) {
            System.out.println(file.getId());
            System.out.println(file.getChunkSize());
            System.out.println(file.getFilename());
            System.out.println(file.getLength());
            System.out.println(file.getMD5());
            System.out.println(file.getMetadata());
            System.out.println("========================================");
        }
    }
    
    @Test
    public void testReactive() {
        Person person = new Person();
        person.setName("Reactive Test");
        //异步的
        Mono<Person> mono = this.reactiveMongoTemplate.save(person);
        System.out.println(mono.block());
    }
    
}
