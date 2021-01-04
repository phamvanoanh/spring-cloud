package com.edu.springbootcsv;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class StudentWriter implements ItemWriter<Object> {
    @Override
    public void write(List<?> list) throws Exception {
        list.forEach(System.out::println);
    }
}
