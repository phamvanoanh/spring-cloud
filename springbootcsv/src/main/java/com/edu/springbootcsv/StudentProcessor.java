package com.edu.springbootcsv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StudentProcessor implements ItemProcessor<StudentDto, StudentDto> {
    @Override
    public StudentDto process(StudentDto studentDto) throws Exception {

        log.info("dto {}", studentDto);
        return null;
    }
}
