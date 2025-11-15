package com.example.upiparser.parser;

import com.example.upiparser.model.Transaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSParser {

    private static final Pattern smsPattern = Pattern.compile("₹(\\d+)\\s+to\\s+([A-Za-z]+)", Pattern.CASE_INSENSITIVE);
    public Transaction parse(String sms) {
        Matcher m = smsPattern.matcher(sms);

        if (m.find()) {
            double amount = Double.parseDouble(m.group(1));
            String merchant = m.group(2);
            return new Transaction(amount, merchant);
        }
        return null;
    }
}
