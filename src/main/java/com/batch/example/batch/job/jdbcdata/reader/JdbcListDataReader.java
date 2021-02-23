package com.batch.example.batch.job.jdbcdata.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.batch.example.batch.job.jdbcdata.dao.entity.People;
import com.batch.example.batch.job.jdbcdata.dao.repository.PeopleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdbcListDataReader implements ItemReader<List<People>> {

    private final PeopleRepository peopleRepository;
    private int lastSeq = 0;
    private int limit = 2;

    @Override
    public List<People> read()  throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        PageRequest pageable = PageRequest.of(lastSeq, limit);
        Page<People> personPage = peopleRepository.findAll(pageable);
        if (personPage.isEmpty()) {
            return null;
        }

        lastSeq = (lastSeq + limit) - 1;

        return personPage.getContent();
    }
}
