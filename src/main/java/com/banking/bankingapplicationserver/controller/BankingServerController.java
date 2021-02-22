package com.banking.bankingapplicationserver.controller;


import com.banking.bankingapplicationserver.entity.Transaction;
import com.banking.bankingapplicationserver.dto.TransactionRequest;
import com.banking.bankingapplicationserver.service.BankingApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class BankingServerController {

    @Autowired
    private BankingApplication bankingApplication;


    @GetMapping(value = "/getbalance/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public double getBalance(@PathVariable(value = "id") long  userId) {
        return bankingApplication.getBalance(userId);
    }

    @PostMapping(value = "/updatebalance", produces = MediaType.APPLICATION_JSON_VALUE)
    public double updateBalance(@RequestBody TransactionRequest request) {
        return bankingApplication.updateBalance(request.getInitiator(), request.getAmount());
    }

    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean transfer(@RequestBody TransactionRequest request) {
        return bankingApplication.transfer(request.getInitiator(), request.getReceiver(), request.getAmount());
    }

    @GetMapping(value = "/generateMiniStatement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Transaction> generateMiniStatement(@PathVariable(value = "id") long userid) {
        return bankingApplication.generateMiniStatement(userid);
    }

    @GetMapping(value = "/generateDetailedStatement", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Transaction> generateDetailedStatement(@RequestParam(defaultValue = "test") long userId, @RequestParam(defaultValue = "test") String startDate, @RequestParam(defaultValue = "test") String endDate) throws ParseException {
        return bankingApplication.generateDetailedStatement(userId, startDate, endDate);
    }


}
