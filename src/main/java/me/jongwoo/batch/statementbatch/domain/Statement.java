package me.jongwoo.batch.statementbatch.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statement {
    private final Customer customer;

    List<Account> accounts = new ArrayList<>();

    public Statement(Customer customer, List<Account> accounts) {
        this.customer = customer;
        accounts.addAll(accounts);
    }

    public Statement(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts.addAll(accounts);
    }
}
