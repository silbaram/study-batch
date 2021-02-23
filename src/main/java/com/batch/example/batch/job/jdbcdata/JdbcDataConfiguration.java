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

import com.batch.example.batch.job.jdbcdata.dao.entity.People;
import com.batch.example.batch.job.jdbcdata.dao.repository.PeopleRepository;
import com.batch.example.batch.job.processing.PersonItemProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcDataConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PeopleRepository personRepository;

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
            .<People, People>chunk(chunkSize)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public RepositoryItemReader<People> reader() {
        return new RepositoryItemReaderBuilder<People>()
            .repository(personRepository)
            .pageSize(chunkSize)
            .methodName("findAll")
            .sorts(Collections.singletonMap("personId", Direction.ASC))
            .name("personRepository")
            .build();
    }

    private ItemWriter<People> writer() {
        return items -> {
            for (People person : items) {
                log.info("Current Person : " + person);
            }
        };
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
}