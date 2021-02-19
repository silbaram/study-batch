package com.batch.example.batch.job.jdbcdata.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;
import com.batch.example.batch.job.jdbcdata.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdbcListDataReader implements ItemReader<List<Person>> {

    private final PersonRepository personRepository;
    private int lastSeq = 0;
    private int limit = 2;

    @Override
    public List<Person> read()  throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        PageRequest pageable = PageRequest.of(lastSeq, limit);
        Page<Person> personPage = personRepository.findAll(pageable);
        if (personPage.isEmpty()) {
            return null;
        }

        lastSeq = (lastSeq + limit) - 1;

        return personPage.getContent();
    }
}
