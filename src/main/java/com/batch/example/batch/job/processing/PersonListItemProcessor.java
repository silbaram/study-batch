package com.batch.example.batch.job.processing;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonListItemProcessor implements ItemProcessor<List<Person>, List<Person>> {

    @Override
    public List<Person> process(List<Person> persons) throws Exception {
        persons.forEach(person -> {
            log.info("PersonListItemProcessor Converting (" + person + ")");
        });

        return persons;
    }
}
