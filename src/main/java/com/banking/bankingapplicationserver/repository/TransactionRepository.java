package com.banking.bankingapplicationserver.repository;

import com.banking.bankingapplicationserver.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transaction_info u WHERE u.initiater_id = :var1 or u.receiver_id = :var1 order by created_at desc limit 10",
            nativeQuery = true)
    List<Transaction> findAllByUserId(long var1);


    @Query(value = "SELECT * FROM transaction_info u WHERE u.initiater_id = :var1 or u.receiver_id = :var1 and u.created_at >= :start and u.created_at<=:end order by created_at desc",
            nativeQuery = true)
    List<Transaction> findAllByUserId(long var1, Date start, Date end);

}
