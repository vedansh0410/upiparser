package com.example.upiparser.repository;

import com.example.upiparser.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMerchantIgnoreCase(String merchant);

}
