package com.edu.springbootcsv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;

import javax.annotation.PostConstruct;

@Slf4j
public class StudentReader extends FlatFileItemReader<StudentDto> {


    @PostConstruct
    public void init() {
        //https://baldir.fr/posts/mapping-csv-file-with-multiline-strings-to-java-object-with-spring-batch/

    }

}
