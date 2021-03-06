package com.batch.example.batch.job.partition;

import com.batch.example.batch.job.jdbcdata.dao.entity.People;
import com.batch.example.batch.job.jdbcdata.dao.repository.PeopleRepository;
import com.batch.example.batch.job.processing.PersonItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PartitionConfiguration {

    public static final String JOB_NAME = "partitionBatch";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PeopleRepository peopleRepository;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize;
    private int poolSize;

    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Value("${poolSize:5}")
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Bean(name = JOB_NAME + "_taskPool")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setThreadNamePrefix(JOB_NAME + "Thread");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }

    /**
     * PartitionHandler는 매니저 (마스터) Step이 Worker Step를 어떻게 다룰지를 정의합니다.
     * @return TaskExecutorPartitionHandler
     */
    @Bean(name = JOB_NAME + "_partitionHandler")
    public TaskExecutorPartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(partitionStep());
        partitionHandler.setTaskExecutor(executor());
        partitionHandler.setGridSize(poolSize);

        return partitionHandler;
    }

    @Bean(name = JOB_NAME)
    public Job partitionJob() {
        return jobBuilderFactory.get("partitionJob")
            .start(stepManager())
            .preventRestart()
            .build();
    }

    @Bean(name = JOB_NAME + "_stepManager")
    public Step stepManager() {
        return stepBuilderFactory.get("partitionStep.stepManager")
            .partitioner("step1", partitioner())
            .step(partitionStep())
            .partitionHandler(partitionHandler())
            .build();
    }

    @Bean(name = JOB_NAME +"_partitioner")
    public PeopleIdRangePartitioner partitioner() {
        return new PeopleIdRangePartitioner(peopleRepository);
    }


    @Bean(name = JOB_NAME + "_partitionStep")
    public Step partitionStep() {
        return stepBuilderFactory.get(JOB_NAME + "_partitionStep")
            .<People, People>chunk(chunkSize)
            .reader(reader(null, null))
            .processor(processor())
            .writer(writer(null, null))
            .build();
    }

    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public JpaPagingItemReader<People> reader(@Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {

        Map<String, Object> params = new HashMap<>();
        params.put("minId", minId);
        params.put("maxId", maxId);

        log.info("reader minId={}, maxId={}", minId, maxId);

        return new JpaPagingItemReaderBuilder<People>()
            .name(JOB_NAME + "reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString(
                "SELECT p " +
                "FROM People p " +
                "WHERE p.personId BETWEEN :minId AND :maxId"
            )
            .parameterValues(params)
            .build();
    }

    @Bean(name = JOB_NAME + "_processor")
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean(name = JOB_NAME + "_writer")
    @StepScope
    public ItemWriter<People> writer(@Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {

        return items -> {
            log.info("stepExecutionContext minId={}, current items={}", minId, items);
            log.info("stepExecutionContext maxId={}, current items={}", maxId, items);
        };
    }
}
