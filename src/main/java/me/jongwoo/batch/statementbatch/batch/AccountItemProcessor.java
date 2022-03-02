package me.jongwoo.batch.statementbatch.batch;

import lombok.RequiredArgsConstructor;
import me.jongwoo.batch.statementbatch.domain.AccountResultSetExtractor;
import me.jongwoo.batch.statementbatch.domain.Statement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// 하나의 계좌에 여러 거래 정보가 있는 부모 자식 관계이므로 RowMapper 사용x
// ResultSetExtractor 사용
// RowMapper는 단일 행을 객체에 매핑
// ResultSetExtractor는 ResultSet 전체를 본다
public class AccountItemProcessor implements ItemProcessor<Statement, Statement> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Statement process(Statement statement) throws Exception {
        statement.setAccounts(this.jdbcTemplate.query("select a.account_id, " +
                "a.balance, " +
                "a.last_statement_date, " +
                "t.transaction_id, " +
                "t.description, " +
                "t.credit, " +
                "t.debit, " +
                "t.timestamp " +
                "from account a left join " +
                "transaction t on a.account_id = t.account_account_id " +
                "where a.account_id in " +
                "(select account_account_id " +
                "from customer_account " +
                "where customer_customer_id = ?) " +
                "order by t.timestamp", new AccountResultSetExtractor(), statement.getCustomer().getId()));

        return statement;
    }
}
