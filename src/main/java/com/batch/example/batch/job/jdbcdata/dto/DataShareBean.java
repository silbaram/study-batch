package com.batch.example.batch.job.jdbcdata.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.batch.example.batch.job.jdbcdata.dto.result.PersonResult;

@Component
public class DataShareBean<T extends PersonResult> {

    private Map<Integer, T> shareDataMap;

    public DataShareBean() {
        shareDataMap = new HashMap<>();
    }

    public void putData(Integer key, T data) {
        if (shareDataMap ==  null) {
            return;
        }

        shareDataMap.put(key, data);
    }

    public T getData (Integer key) {

        if (shareDataMap == null) {
            return null;
        }

        return shareDataMap.get(key);
    }

    public List<Integer> getAllData() {
        return shareDataMap.values().stream().map(t -> t.getPersonId()).collect(Collectors.toList());
    }

    public int getSize () {
        if (this.shareDataMap == null) {
            return 0;
        }

        return shareDataMap.size();
    }
}
