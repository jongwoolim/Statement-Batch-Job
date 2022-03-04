package me.jongwoo.batch.statementbatch.batch;

import lombok.RequiredArgsConstructor;
import me.jongwoo.batch.statementbatch.domain.CustomerUpdate;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomerItemValidator implements Validator<CustomerUpdate> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String FIND_CUSTOMER = "SELECT COUNT(*) FROM CUSTOMER WHERE customer_id = :id";

    @Override
    public void validate(CustomerUpdate customerUpdate) throws ValidationException {

        Map<String, Long> parameterMap = Collections.singletonMap("id", customerUpdate.getCustomerId());

        final Long count = jdbcTemplate.queryForObject(FIND_CUSTOMER, parameterMap, Long.class);

        if(count == 0){
            throw new ValidationException(String.format("Customer id %s was not able to be found", customerUpdate.getCustomerId()));
        }

    }
}
