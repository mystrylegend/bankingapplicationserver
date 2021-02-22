package com.banking.bankingapplicationserver;

public enum TransactionType {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
