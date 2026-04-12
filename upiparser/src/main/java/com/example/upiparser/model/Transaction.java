package com.example.upiparser.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String merchant;

    private String smsText;

    private long timestamp;

    public Transaction() {}

    public Transaction(double amount, String merchant, String smsText, long timestamp) {
        this.amount = amount;
        this.merchant = merchant;
        this.smsText = smsText;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public String getMerchant() { return merchant; }
    public String getSmsText() { return smsText; }
    public long getTimestamp() { return timestamp; }
}
