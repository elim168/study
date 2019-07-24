package com.elim.study.dubbo.service;

import com.elim.study.dubbo.model.Person;

public class PersonServiceImpl implements PersonService {
    @Override
    public Person findById(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setName("Person-" + id);
        return person;
    }
}
