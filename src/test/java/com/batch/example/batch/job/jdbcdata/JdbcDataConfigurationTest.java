package com.batch.example.batch.job.jdbcdata;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.batch.example.TestBatchConfig;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBatchTest
@SpringBootTest(classes={JdbcDataConfiguration.class, TestBatchConfig.class, BatchAutoConfiguration.class})
@EnableAutoConfiguration

//@ExtendWith(SpringExtension.class)
//@SpringBatchTest
//@ContextConfiguration(classes = {JdbcDataConfiguration.class,
//    BatchAutoConfiguration.class})
@TestPropertySource(properties = "debug=true")
public class JdbcDataConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @Transactional
    public void jdbcDataJobTest() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("version", "7")
            .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
    }
}