package com.batch.example.batch.job.processing;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.example.batch.job.jdbcdata.dao.entity.People;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonListItemProcessor implements ItemProcessor<List<People>, List<People>> {

    @Override
    public List<People> process(List<People> persons) throws Exception {
        persons.forEach(person -> {
            log.info("PersonListItemProcessor Converting (" + person + ")");
        });

        return persons;
    }
}
