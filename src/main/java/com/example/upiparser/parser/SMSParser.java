package com.example.upiparser.parser;

import com.example.upiparser.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSParser {
	
	private static final Logger logger = LoggerFactory.getLogger(SMSParser.class);


    private static final Pattern smsPattern = Pattern.compile("₹(\\d+)\\s+to\\s+([A-Za-z]+)", Pattern.CASE_INSENSITIVE);
    public Transaction parse(String sms) {
        Matcher m = smsPattern.matcher(sms);

        logger.info("Parsing SMS: {}", sms);
        logger.debug("Regex Pattern Used: {}", smsPattern.pattern());

        
        if (m.find()) {
        	logger.info("Match found! Amount={} Merchant={}", m.group(1), m.group(2));
            double amount = Double.parseDouble(m.group(1));
            String merchant = m.group(2);
            return new Transaction(amount, merchant);
        }
        else 
        	logger.warn("No match found for SMS: {}", sms);
        return null;
    }
}
