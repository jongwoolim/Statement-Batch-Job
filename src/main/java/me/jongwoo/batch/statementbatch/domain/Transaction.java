package me.jongwoo.batch.statementbatch.domain;

import me.jongwoo.batch.statementbatch.batch.JaxbDateSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement
public class Transaction {

    private long transactionId;
    private long accountId;
    private String description;
    private BigDecimal credit;
    private BigDecimal debit;
    private Date timestamp;

    public Transaction(
            long transactionId, long accountId, String description,
            BigDecimal credit, BigDecimal debit, Date timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.description = description;
        this.credit = credit;
        this.debit = debit;
        this.timestamp = timestamp;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    // JAX-B는 String을 Date로 변환하는 도구를 별도로 제공하지 않음
    @XmlJavaTypeAdapter(JaxbDateSerializer.class)
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTransactionAmount() {
        if(credit != null) {
            if(debit != null) {
                return credit.add(debit);
            }
            else {
                return credit;
            }
        }
        else if(debit != null) {
            return debit;
        }
        else {
            return new BigDecimal(0);
        }
    }
}
