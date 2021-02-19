package com.batch.example.batch.job;

import org.springframework.batch.core.ExitStatus;
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
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get("stepNextConditionalJob")
            .start(conditionalJobStep1())
                .on(ExitStatus.FAILED.getExitCode()).to(conditionalJobStep3())
                .on("*")
                .end()
            .from(conditionalJobStep1())
                .on("*").to(conditionalJobStep2())
                .next(conditionalJobStep3())
                .on("*")
                .end()
            .from(conditionalJobStep1())
                .on(ExitStatus.COMPLETED.getExitCode())
                .end()
            .end()
            .build();
    }

    @Bean
    public Step conditionalJobStep1() {
        return stepBuilderFactory.get("conditionalJobStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info((">>>>> This is stepNextConditionalJob Step1"));

//                contribution.setExitStatus(ExitStatus.FAILED);
                contribution.setExitStatus(ExitStatus.COMPLETED);

                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get("conditionalJobStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalJob Step2");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step conditionalJobStep3() {
        return stepBuilderFactory.get("conditionalJobStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalJob Step3");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
