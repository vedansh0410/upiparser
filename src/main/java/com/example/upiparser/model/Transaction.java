package com.example.upiparser.model;

public class Transaction {

    private double amount;
    private String merchant;

    public Transaction(double amount, String merchant) {
        this.amount = amount;
        this.merchant = merchant;
    }

	public String getMerchant() {
		// TODO Auto-generated method stub
		return merchant;
	}

	public double getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}


}
