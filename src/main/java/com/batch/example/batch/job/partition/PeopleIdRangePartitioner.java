package com.batch.example.batch.job.partition;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import com.batch.example.batch.job.jdbcdata.dao.repository.PeopleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PeopleIdRangePartitioner implements Partitioner {

    private final PeopleRepository peopleRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long min = peopleRepository.findMinId();
        long max = peopleRepository.findMaxId();
        long targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();
        long number = 0;
        long start = min;
        long end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }

            value.putLong("minId", start); // 각 파티션마다 사용될 minId
            value.putLong("maxId", end); // 각 파티션마다 사용될 maxId
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
