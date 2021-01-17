package com.edu.springbootcsv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class StudentReader extends FlatFileItemReader<StudentDto> {

    public static void main(String[] args) {
        final List<String> split1 = Arrays.stream("id,age".split(",")).collect(Collectors.toList());
        final List<String> header = Arrays.stream("id,legalId,name,age".split(",")).collect(Collectors.toList());

        for (int i = 0; i < header.size(); i++) {

            if(header.get(i).equals(split1.get(i))) {

            } else {
                split1.add(1, null);
            }

        }

        split1.forEach(System.out::println);
        final String[] processors = new String[] { "not Null", "not Null",
                          "not Null", null, null, null, null, null, null, null};

        //https://super-csv.github.io/super-csv/xref-test/org/supercsv/example/Reading.html

        // https://github.com/spring-projects/spring-batch/tree/master/spring-batch-samples

        // https://github.com/spring-projects/spring-batch/tree/master/spring-batch-samples/src/main/java/org/springframework/batch/sample/common
        
    }



    //https://baldir.fr/posts/mapping-csv-file-with-multiline-strings-to-java-object-with-spring-batch/
    /**
     * https://super-csv.github.io/super-csv/examples_partial_reading.html
     */


}
