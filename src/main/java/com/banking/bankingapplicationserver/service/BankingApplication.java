package com.banking.bankingapplicationserver.service;

import com.banking.bankingapplicationserver.entity.Transaction;

import java.text.ParseException;
import java.util.Date;
import java.util.Collection;

public interface BankingApplication {

    /** Get current balance in user account
     * @param user*/

    double getBalance(long user);

    /** Deposit or debit money into a user account. should return the updated balance. */

    double updateBalance(long user, double amount );

    /** Transfer balance from one user account to another. Handle all edge cases gracefully and specify the expected behavior in comments. */

    boolean transfer(long sender, long receiver, double amount);

    /** Retrieve last 10 transactions on the user account.
     * @param user*/

    Collection<Transaction> generateMiniStatement(long user);

    /**

     Retrieve all transactions for the user in the specified period. Use filesystem or an embedded database like SQLite/H2 for persistence. */

    Collection<Transaction> generateDetailedStatement(long user, String start, String end) throws ParseException;

}
