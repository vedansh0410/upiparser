package com.example.upiparser.service;

import com.example.upiparser.dto.TransactionDTO;
import com.example.upiparser.model.Transaction;
import com.example.upiparser.parser.SMSParser;
import com.example.upiparser.repository.TransactionRepository;
import com.example.upiparser.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    private final SMSParser parser = new SMSParser();

    // MAIN FUNCTION: Parse SMS → Save to DB
    public Transaction parseAndSave(TransactionDTO dto) {

        // 1. Save raw SMS locally
        FileUtil.saveRawSMS(dto.getSmsText());

        // 2. Parse SMS
        SMSParser.ParsedData data = parser.parse(dto.getSmsText());

        if (data == null) return null; // invalid SMS

        // 3. Save to DB
        Transaction saved = repo.save(
                new Transaction(
                        data.amount,
                        data.merchant,
                        dto.getSmsText(),
                        System.currentTimeMillis()
                )
        );

        return saved;
    }


    // Get all transactions from DB
    public List<Transaction> getAll() {
        return repo.findAll();
    }


    // Total spend
    public double getTotalSpend() {
        return repo.findAll()
                   .stream()
                   .mapToDouble(Transaction::getAmount)
                   .sum();
    }


    // Total count
    public int getTransactionCount() {
        return (int) repo.count();
    }


    // Merchant count map
    public Map<String, Integer> getMerchantCount() {
        List<Transaction> list = repo.findAll();
        Map<String, Integer> map = new HashMap<>();

        for (Transaction t : list) {
            map.put(t.getMerchant(), map.getOrDefault(t.getMerchant(), 0) + 1);
        }
        return map;
    }


    // Filter by merchant (case-insensitive)
    public List<Transaction> getByMerchant(String merchant) {
        if (merchant == null || merchant.isBlank()) return Collections.emptyList();
        return repo.findByMerchantIgnoreCase(merchant.trim());
    }
}



