package me.jongwoo.batch.statementbatch.batch;

import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.stereotype.Component;

@Component
public class CustomerOutputFileSuffixCreator implements ResourceSuffixCreator {

    @Override
    public String getSuffix(int i) {
        return i+".csv";
    }
}
