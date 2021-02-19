package com.batch.example.batch.job.jdbcdata.dto.result;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PersonResult {

    private int personId;
    private String step1Mame;
    private String stet2Mame;
}
