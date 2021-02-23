package com.batch.example.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextJob() {
        return jobBuilderFactory.get("stepNextJob")
            .start(nextStep1())
            .next(nextStep2())
            .next(nextStep3())
            .build();
    }

    @Bean
    public Step nextStep1() {
        return stepBuilderFactory.get("nextStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is nextStep1");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step nextStep2() {
        return stepBuilderFactory.get("nextStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is nextStep2");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    public Step nextStep3() {
        return stepBuilderFactory.get("nextStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is nextStep3");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
