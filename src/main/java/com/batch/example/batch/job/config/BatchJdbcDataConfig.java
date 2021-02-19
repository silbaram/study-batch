//package com.batch.example.batch.job.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//
//@Configuration
//@EnableJdbcRepositories(basePackages = "com.batch.example.batch.job.jdbcdata.repository")
//public class BatchJdbcDataConfig {
//
//    @Primary
//    @Bean
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//
//        return builder.build();
//    }
//}
