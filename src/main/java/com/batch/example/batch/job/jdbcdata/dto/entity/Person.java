package com.batch.example.batch.job.jdbcdata.dto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Table("people")
public class Person {

    @Id
    @Column("person_id")
    private int personId;
    private final String name;

    @Override
    public String toString() {
        return "name: " + name;
    }
}
