package com.banking.bankingapplicationserver.service;


import com.banking.bankingapplicationserver.entity.Transaction;
import com.banking.bankingapplicationserver.entity.User;
import com.banking.bankingapplicationserver.repository.TransactionRepository;
import com.banking.bankingapplicationserver.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
public class BankingApplicationServiceImplTest {


    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    BankingApplicationServiceImpl bankingApplicationService;

    @Before
    public void setup() {
        bankingApplicationService = new BankingApplicationServiceImpl();
        bankingApplicationService.setTransactionRepository(transactionRepository);
        bankingApplicationService.setUserRepository(userRepository);

    }


    @Test
    public void testGetBalance() {
        Mockito.when(userRepository.findById(any())).thenReturn(getUserData());

        double result = bankingApplicationService.getBalance(1);
        Assert.assertTrue(result == 100.00);

    }


    @Test
    public void testGenerateMiniStatement() {

        Mockito.when(userRepository.findById(any())).thenReturn(getUserData());
        Mockito.when(transactionRepository.findAllByUserId(anyLong())).thenReturn((List<Transaction>) getListOfTransactions());
        List<Transaction> transactionList = (List)bankingApplicationService.generateMiniStatement(2);
        Assert.assertTrue(transactionList.size()==3);
        Assert.assertTrue(transactionList.get(0).getAmount()==100.00);
        Assert.assertTrue(transactionList.get(1).getInitiater()==3);
        Assert.assertTrue(transactionList.get(2).getReceiver()==2);

    }


    public static Optional<User> getUserData() {
        User user = new User();
        user.setTotalBalance(100);
        user.setFirstName("raju");
        user.setLastName("babu");
        return Optional.of(user);
    }


    public static Collection<Transaction> getListOfTransactions() {
        List<Transaction> list = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 2, 100);
        Transaction transaction2 = new Transaction(3, 2, 300);
        Transaction transaction3 = new Transaction(4, 2, 500);
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);
        return list;
    }
}
