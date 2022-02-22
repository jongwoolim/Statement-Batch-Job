package me.jongwoo.batch.statementbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class StatementBatchJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatementBatchJobApplication.class, args);
    }

}
