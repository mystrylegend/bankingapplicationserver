package com.banking.bankingapplicationserver.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction_info")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transactionId;
    @Column(name = "receiver_id")
    private long receiver;
    @Column(name = "initiater_id")
    private long initiater;
    @Column(name = "amount")
    private double amount;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    Transaction(){}


    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public long getInitiater() {
        return initiater;
    }

    public void setInitiater(long initiater) {
        this.initiater = initiater;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Transaction(long initiater, long receiver, double amount) {
        this.receiver = receiver;
        this.initiater = initiater;
        this.amount = amount;
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", receiver=" + receiver +
                ", initiater=" + initiater +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
