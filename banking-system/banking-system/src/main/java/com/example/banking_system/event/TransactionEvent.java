package com.example.banking_system.event;

import com.example.banking_system.model.Transaction;

public class TransactionEvent {

    private Transaction transaction;

    public TransactionEvent() {}

    public TransactionEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}