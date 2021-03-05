package com.batch.example.batch.job.jdbcdata.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.batch.example.batch.job.jdbcdata.dao.entity.People;

public interface PeopleRepository extends JpaRepository<People, Long> {

    List<People> findAllByPersonIdIn(@Param("person_id") List<Integer> persons);

    @Query("SELECT MIN(p.personId) FROM People p")
    Long findMinId();

    @Query("SELECT MAX (p.personId) FROM People p")
    Long findMaxId();
}
