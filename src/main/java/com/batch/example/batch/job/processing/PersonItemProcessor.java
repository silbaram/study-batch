package com.batch.example.batch.job.processing;

import org.springframework.batch.item.ItemProcessor;

import com.batch.example.batch.job.jdbcdata.dao.entity.People;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<People, People> {

    @Override
    public People process(People person) throws Exception {
        final String name = person.getName();
        final People transformedPerson = People.builder().name(name.toUpperCase()).build();


        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
