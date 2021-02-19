package com.batch.example.batch.job.jdbcdata.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.batch.example.batch.job.jdbcdata.dto.entity.Person;

public interface PersonRepository extends PagingAndSortingRepository<Person, Person> {

    List<Person> findAllByPersonIdIn(@Param("person_id") List<Integer> persons);
}
