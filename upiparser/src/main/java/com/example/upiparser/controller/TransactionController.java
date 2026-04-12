package com.example.upiparser.controller;

import com.example.upiparser.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	// Logger to track API requests, responses, and debugging information
	 private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService service;

    @PostMapping("/parse")
    public ResponseEntity<ApiResponse<?>> parseSMS(@RequestBody TransactionDTO dto) {
    	
    	// Log incoming SMS before parsing
    	logger.info("Received SMS parse request: {}", dto.getSmsText());
    	
        Transaction t = service.parseAndSave(dto);

        if (t == null) {
        	// Warn when no valid transaction is found in SMS
        	logger.warn("Parsing failed. No transaction detected.");
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error("Unable to parse SMS"));
        }
        
        // Log parsed transaction details
        logger.info("Parsing completed successfully. Merchant={} Amount={}", t.getMerchant(), t.getAmount());
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
    
    @GetMapping("/merchant/{name}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getByMerchant(@PathVariable String name) {
        List<Transaction> list = service.getByMerchant(name);
        if (list == null || list.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(ApiResponse.error("No transactions found for merchant: " + name));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Transactions for merchant: " + name, list));
    }
    
    //Total Transitions count
    @GetMapping("/count")
    public ApiResponse<Integer> getTransactionCount() {
        return new ApiResponse<>(
                true,
                "Total transactions count fetched",
                service.getTransactionCount()
        );
    }

}
