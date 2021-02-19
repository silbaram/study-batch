package com.batch.example.batch.job.jdbcdata;

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;
import com.batch.example.batch.job.jdbcdata.repository.PersonRepository;
import com.batch.example.batch.job.processing.PersonItemProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcDataConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PersonRepository personRepository;

    private static final int chunkSize = 2;

    @Bean
    public Job jdbcDataJob() {
        return jobBuilderFactory.get("jdbcDataJob")
            .start(step1())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<Person, Person>chunk(chunkSize)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public RepositoryItemReader<Person> reader() {
        return new RepositoryItemReaderBuilder<Person>()
            .repository(personRepository)
            .pageSize(chunkSize)
            .methodName("findAll")
            .sorts(Collections.singletonMap("person_id", Direction.ASC))
            .name("personRepository")
            .build();
    }

    private ItemWriter<Person> writer() {
        return items -> {
            for (Person person : items) {
                log.info("Current Person : " + person);
            }
        };
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
}