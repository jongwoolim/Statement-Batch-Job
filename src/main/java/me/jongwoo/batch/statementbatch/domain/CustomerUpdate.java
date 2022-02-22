package me.jongwoo.batch.statementbatch.domain;

public class CustomerUpdate {

    protected final long customerId;

    public CustomerUpdate(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "CustomerUpdate{" +
                "customerId=" + customerId +
                '}';
    }
}
