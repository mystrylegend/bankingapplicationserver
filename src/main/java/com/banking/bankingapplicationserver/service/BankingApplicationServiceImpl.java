package com.banking.bankingapplicationserver.service;

import com.banking.bankingapplicationserver.TransactionType;
import com.banking.bankingapplicationserver.entity.Transaction;
import com.banking.bankingapplicationserver.entity.User;
import com.banking.bankingapplicationserver.repository.TransactionRepository;
import com.banking.bankingapplicationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class BankingApplicationServiceImpl implements BankingApplication {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public double getBalance(long user) {
        Optional<User> users = userRepository.findById(user);
        return users.get().getTotalBalance();
    }

    @Transactional
    @Override
    public double updateBalance(long user, double amount) {
        try {
            Optional<User> users = userRepository.findById(user);
            User user1 = users.get();
            double updatedBalance = 0;
            if (Objects.nonNull(user1)) {
                Transaction transaction = new Transaction(user, user, amount);
                transactionRepository.save(transaction);
                updatedBalance = user1.getTotalBalance() + amount;
                user1.setTotalBalance(updatedBalance);
                userRepository.save(user1);
            }
            return updatedBalance;
        } catch (Exception ex) {
            System.out.println("User does not exist so transaction failed");
        }

        return 0;
    }


    @Override
    @Transactional
    public boolean transfer(long sender, long receiver, double amount) {
        boolean result = false;
        try {
            Optional<User> user1 = userRepository.findById(sender);
            Optional<User> user2 = userRepository.findById(receiver);

            if (user1.isPresent() && user2.isPresent()) {
                User initiatorUser = user1.get();
                User receiverUser = user2.get();
                if (initiatorUser.getTotalBalance() >= amount) {
                    Transaction transaction = new Transaction(sender, receiver, amount);
                    transactionRepository.save(transaction);
                    initiatorUser.setTotalBalance(initiatorUser.getTotalBalance() - amount);
                    receiverUser.setTotalBalance(receiverUser.getTotalBalance() + amount);
                    userRepository.save(initiatorUser);
                    userRepository.save(receiverUser);
                    result = true;
                }
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Transaction failed");
        }
        return result;
    }

    @Override
    public Collection<Transaction> generateMiniStatement(long user) {
        Optional<User> users = userRepository.findById(user);
        User user1 = users.get();
        if (Objects.nonNull(user1)) {
            return transactionRepository.findAllByUserId(user1.getId());

        }
        return null;
    }

    @Override
    public Collection<Transaction> generateDetailedStatement(long user, String start, String end) throws ParseException {


        Optional<User> users = userRepository.findById(user);
        User user1 = users.get();
        if (Objects.nonNull(user1)) {
            return transactionRepository.findAllByUserId(user1.getId(), getFormatedDate(start), getFormatedDate(end));

        }
        return null;
    }


    private static Date getFormatedDate(String inputDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);

    }
}
