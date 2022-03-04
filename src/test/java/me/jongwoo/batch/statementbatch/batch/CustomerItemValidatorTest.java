package me.jongwoo.batch.statementbatch.batch;

import me.jongwoo.batch.statementbatch.domain.CustomerUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

class CustomerItemValidatorTest {

    @Mock
    private NamedParameterJdbcTemplate template;

    private CustomerItemValidator validator;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.validator = new CustomerItemValidator(this.template);
    }

    @Test
    public void testValidCustomer(){
        //given
        CustomerUpdate customerUpdate = new CustomerUpdate(5l);
        //when
        ArgumentCaptor<Map<String,Long>> parameterMap = ArgumentCaptor.forClass(Map.class);

        when(this.template.queryForObject(
                eq(CustomerItemValidator.FIND_CUSTOMER),
                parameterMap.capture(),
                eq(Long.class)))
                .thenReturn(0l);

//        this.validator.validate(customerUpdate);

        Throwable exception = assertThrows(ValidationException.class, () -> this.validator.validate(customerUpdate));
        assertEquals("Customer id 5 was not able to be found", exception.getMessage());

        //then
//        assertEquals(5l, (long)parameterMap.getValue().get("id"));
    }

}