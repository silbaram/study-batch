package com.batch.example.batch.job.jdbcdata;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;
import com.batch.example.batch.job.jdbcdata.reader.JdbcListDataReader;
import com.batch.example.batch.job.processing.PersonListItemProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcListDataConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JdbcListDataReader jdbcListDataReader;

    private static final int chunkSize = 1;

    @Bean
    public Job jdbcListDataJob() {
        return jobBuilderFactory.get("jdbcListDataJob")
            .start(step())
            .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
            .<List<Person>, List<Person>>chunk(chunkSize)
            .reader(jdbcListDataReader)
            .processor(new PersonListItemProcessor())
            .writer(writer())
            .build();
    }

    private ItemWriter<List<Person>> writer() {
        return items -> {
            for (List<Person> persons : items) {
                for (Person person : persons) {
                    log.info("Current Person : " + person);
                }
            }
        };
    }
}