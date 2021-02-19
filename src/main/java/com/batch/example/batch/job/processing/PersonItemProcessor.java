package com.batch.example.batch.job.processing;

import org.springframework.batch.item.ItemProcessor;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        final String name = person.getName();
        final Person transformedPerson = new Person(name.toUpperCase());

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
