package com.example.upiparser.controller;

import com.example.upiparser.dto.TransactionDTO;
import com.example.upiparser.model.Transaction;
import com.example.upiparser.payload.ApiResponse;
import com.example.upiparser.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/parse")
    public ApiResponse<Transaction> parseSMS(@RequestBody TransactionDTO dto) {
        Transaction t = service.parseAndSave(dto);
        return new ApiResponse<>(true, "Transaction parsed successfully", t);
    }
    @GetMapping
    public ApiResponse<List<Transaction>> getAll() {
        return new ApiResponse<>(true, "All transactions fetched", service.getAll());
    }

    @GetMapping("/total")
    public double getTotalSpend() {
        return service.getTotalSpend();
    }

    @GetMapping("/merchant-count")
    public Map<String, Integer> getMerchantCount() {
        return service.getMerchantCount();
    }
}
