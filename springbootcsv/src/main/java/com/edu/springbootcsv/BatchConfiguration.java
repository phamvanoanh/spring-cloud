package com.edu.springbootcsv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@Slf4j
public class BatchConfiguration {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Bean
    Job job() {
        return this.jobBuilderFactory.get("csvjob")
                .incrementer(new RunIdIncrementer())
                .listener(new Listener())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return this.stepBuilderFactory.get("csvstep")
                .<StudentDto, StudentDto>chunk(1)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(objectItemWriter())
                .build();
    }
    @Bean
    public ItemWriter<Object> objectItemWriter() {
        return new StudentWriter();
    }

    @Bean
    public ItemProcessor<StudentDto, StudentDto> itemProcessor() {
        return new StudentProcessor();
    }
    @Bean
    @StepScope
    public ItemReader<StudentDto> itemReader() {
            //Create reader instance
            FlatFileItemReader<StudentDto> reader = new FlatFileItemReader<StudentDto>();

            //Set input file location
            reader.setResource(new FileSystemResource("data/students.csv"));

            //Set number of lines to skips. Use it if file has header rows.
            reader.setLinesToSkip(1);

            //Configure how each line will be parsed and mapped to different values
            reader.setLineMapper(new DefaultLineMapper() {
                {
                    //3 columns in each row
                    setLineTokenizer(new DelimitedLineTokenizer() {
                        {
                            setNames(new String[] { "id", "name", "EMAIL_ADDRESS" });
                        }
                    });
                    //Set values in StudentDto class
                    setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentDto>() {
                        {
                            setTargetType(StudentDto.class);
                        }
                    });
                }
            });
            return reader;
//        LineMapper<StudentDto> studentLineMapper = createStudentLineMapper();
//        return new FlatFileItemReaderBuilder<StudentDto>()
//                .name("studentReader")
//                .resource(new ClassPathResource("data/students.csv"))
//                .linesToSkip(1)
//                .lineMapper(studentLineMapper)
//                .build();
    }
    private LineMapper<StudentDto> createStudentLineMapper() {
        DefaultLineMapper<StudentDto> studentLineMapper = new DefaultLineMapper<>();
        LineTokenizer studentLineTokenizer = createStudentLineTokenizer();
        studentLineMapper.setLineTokenizer(studentLineTokenizer);
        FieldSetMapper<StudentDto> studentInformationMapper = createStudentInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);
        return studentLineMapper;
    }

    private LineTokenizer createStudentLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames(new String[]{
                "id",
                "name",
                "emailAddress",
                "purchasedPackage"
        });
        return studentLineTokenizer;
    }

    private FieldSetMapper<StudentDto> createStudentInformationMapper() {
        BeanWrapperFieldSetMapper<StudentDto> studentInformationMapper = new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(StudentDto.class);
        return studentInformationMapper;
    }

}
@Slf4j
class Listener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("start---------------------------");


    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("end---------------------------");
    }
}
