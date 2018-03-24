/**
 * 
 */
package com.elim.learn.springboot.data.mongodb;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.elim.learn.springboot.data.mongodb.entity.Person;

/**
 * @author Elim
 * 2018年3月24日
 */
public interface PersonRepository extends CrudRepository<Person, BigInteger> {

    @Query("{age: {$lt: 32}}")
    List<Person> findAllByAges(int age);
    
}
