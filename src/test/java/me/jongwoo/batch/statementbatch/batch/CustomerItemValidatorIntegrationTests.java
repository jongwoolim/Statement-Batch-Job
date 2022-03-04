package me.jongwoo.batch.statementbatch.batch;

import me.jongwoo.batch.statementbatch.domain.CustomerUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JdbcTest
class CustomerItemValidatorIntegrationTests {

    @Autowired
    private DataSource dataSource;

    private CustomerItemValidator customerItemValidator;

    @BeforeEach
    public void setUp(){
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.dataSource);
        this.customerItemValidator = new CustomerItemValidator(template);
    }

    @Test
    public void testNoCustomers(){
        //given
        CustomerUpdate customerUpdate = new CustomerUpdate(-5l);
        //when

        ValidationException exception =
                assertThrows(ValidationException.class, () -> this.customerItemValidator.validate(customerUpdate));
        assertEquals("Customer id -5 was not able to be found", exception.getMessage());

    }

    @Test
    public void testCustomers(){
        CustomerUpdate customerUpdate = new CustomerUpdate(5l);

        this.customerItemValidator.validate(customerUpdate);
    }

}