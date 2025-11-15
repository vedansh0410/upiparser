package com.example.upiparser.service;

import com.example.upiparser.dto.TransactionDTO;
import com.example.upiparser.model.Transaction;
import com.example.upiparser.parser.SMSParser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();
    private final SMSParser parser = new SMSParser();

    public Transaction parseAndSave(TransactionDTO dto) {
        Transaction t = parser.parse(dto.getSmsText());

        if (t != null) {
            transactions.add(t);
        }
        return t;
    }

    public List<Transaction> getAll() {
        return transactions;
    }

    public double getTotalSpend() {
        double total = 0;
        for (Transaction t : transactions) {
            total += t.getAmount();
        }
        return total;
    }

    public Map<String, Integer> getMerchantCount() {
        Map<String, Integer> map = new HashMap<>();

        for (Transaction t : transactions) {
            map.put(t.getMerchant(),
                    map.getOrDefault(t.getMerchant(), 0) + 1);
        }
        return map;
    }
}
