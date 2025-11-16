package com.example.upiparser.controller;

import com.example.upiparser.dto.TransactionDTO;
import com.example.upiparser.model.Transaction;
import com.example.upiparser.payload.ApiResponse;
import com.example.upiparser.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/parse")
    public ResponseEntity<ApiResponse<?>> parseSMS(@RequestBody TransactionDTO dto) {

        Transaction t = service.parseAndSave(dto);

        if (t == null) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error("Unable to parse SMS"));
        }

        return ResponseEntity
                .ok(ApiResponse.success("Parsed successfully", t));
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
