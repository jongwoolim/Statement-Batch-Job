package me.jongwoo.batch.statementbatch.configuration;

import lombok.RequiredArgsConstructor;
import me.jongwoo.batch.statementbatch.batch.CustomerItemValidator;
import me.jongwoo.batch.statementbatch.domain.CustomerAddressUpdate;
import me.jongwoo.batch.statementbatch.domain.CustomerContactUpdate;
import me.jongwoo.batch.statementbatch.domain.CustomerNameUpdate;
import me.jongwoo.batch.statementbatch.domain.CustomerUpdate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ImportJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){

//        return this.jobBuilderFactory.get("importJob")
//                .start(importCustomerUpdates())
//                .build();
        return null;
    }

    @Bean
    public Step importCustomerUpdates() throws Exception {
        return this.stepBuilderFactory.get("importCustomerUpdates")
                .chunk(100)
                .reader(customerUpdateItemReader(null))
//                .processor(customerValidatingItemProcessor(null))
//                .writer(customerUpdateItemWriter())
                .build()
                ;
    }

    @Bean
    @StepScope
    public ItemReader<?> customerUpdateItemReader(
            @Value("#{jobParameters['customerUpdateFile']}") Resource inputFile
    ) throws Exception {
        return new FlatFileItemReaderBuilder<>()
                .name("customerUpdateItemReader")
                .resource(inputFile)
                .lineTokenizer(customerUpdatesLineTokenizer())
                .fieldSetMapper(customerUpdateFieldSetMapper())
                .build();
    }

    @Bean
    public LineTokenizer customerUpdatesLineTokenizer() throws Exception {

        // 한 파일의 세 가지 종류의 레코드 형식이므로 읽어들이기 위해
        // 합성 패턴인 PatternMatchingCompositeLineTokenizer 사용
        DelimitedLineTokenizer recordType1 = new DelimitedLineTokenizer();
        recordType1.setNames("recordId","customerId","firstName","middleName","lastName");

        recordType1.afterPropertiesSet();

        DelimitedLineTokenizer recordType2 = new DelimitedLineTokenizer();
        recordType2.setNames("recordId","customerId","address1","address2","city", "state","postalCode");

        recordType2.afterPropertiesSet();

        DelimitedLineTokenizer recordType3 = new DelimitedLineTokenizer();
        recordType3.setNames("recordId","customerId","emailAddress","homePhone","cellPhone", "workPhone","notificationPreference");

        recordType3.afterPropertiesSet();

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);
        tokenizers.put("1", recordType1);
        tokenizers.put("2", recordType2);
        tokenizers.put("3", recordType3);

        PatternMatchingCompositeLineTokenizer lineTokenizer =
                new PatternMatchingCompositeLineTokenizer();

        lineTokenizer.setTokenizers(tokenizers);

        return lineTokenizer;

    }


    @Bean
    public FieldSetMapper<Object> customerUpdateFieldSetMapper() {
        return fieldSet -> {
            switch (fieldSet.readInt("recordId")){
                case 1:
                    return new CustomerNameUpdate(
                            fieldSet.readLong("customerId"),
                            fieldSet.readString("firstName"),
                            fieldSet.readString("middleName"),
                            fieldSet.readString("lastName")
                    );
                case 2:
                    return new CustomerAddressUpdate(
                            fieldSet.readLong("customerId"),
                            fieldSet.readString("address1"),
                            fieldSet.readString("address2"),
                            fieldSet.readString("city"),
                            fieldSet.readString("state"),
                            fieldSet.readString("postalCode")
                    );
                case 3:
                    String rawPreference =
                            fieldSet.readString("notificationPreference");

                    Integer notificationPreference = null;

                    if(StringUtils.hasText(rawPreference)){
                        notificationPreference = Integer.parseInt(rawPreference);
                    }

                    return new CustomerContactUpdate(
                            fieldSet.readLong("customerId"),
                            fieldSet.readString("emailAddress"),
                            fieldSet.readString("homePhone"),
                            fieldSet.readString("cellPhone"),
                            fieldSet.readString("workPhone"),
                            notificationPreference
                    );

                default: throw new IllegalArgumentException("Invalid record type was found: " + fieldSet.readInt("recordId"));
            }

        };
    }

    @Bean
    public ValidatingItemProcessor<CustomerUpdate> customerValidatingItemProcessor(
            CustomerItemValidator validator){

        ValidatingItemProcessor<CustomerUpdate> customerUpdateValidatingItemProcessor =
                new ValidatingItemProcessor<>(validator);

        customerUpdateValidatingItemProcessor.setFilter(true);

        return customerUpdateValidatingItemProcessor;
    }


}
