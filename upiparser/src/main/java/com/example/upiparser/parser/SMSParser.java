package com.example.upiparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSParser {

    private static final Logger logger = LoggerFactory.getLogger(SMSParser.class);

    /**
     * Updated Regex:
     * - Extract only 1 merchant word (avoid capturing 'via', 'googlepay', etc.)
     * - Supports formats:
     *      debited ₹1600 to Meemansa via Google Pay
     *      Paid ₹500 to Amazon
     *      Sent ₹1200 to Rahul
     */
    private static final Pattern smsPattern = Pattern.compile(
            "(?:₹|Rs\\.?)[\\s]*([0-9]+(?:\\.[0-9]{1,2})?)" +   // amount (₹120 or ₹120.50)
            ".*?(?:to|paid|sent|debited|transfer(?:red)?)\\s+" + // action keywords
            "([A-Za-z]+)",                                       // ONLY 1 merchant word
            Pattern.CASE_INSENSITIVE
    );

    public ParsedData parse(String sms) {

        logger.info("Parsing SMS: {}", sms);
        logger.debug("Regex Pattern Used: {}", smsPattern.pattern());

        if (sms == null || sms.isBlank()) {
            logger.warn("SMS text is empty or null");
            return null;
        }

        Matcher m = smsPattern.matcher(sms);

        if (m.find()) {
            String amountStr = m.group(1).trim();
            String merchant = m.group(2).trim();

            logger.info("Match found! Amount={} Merchant={}", amountStr, merchant);

            try {
                double amount = Double.parseDouble(amountStr);
                return new ParsedData(amount, merchant);

            } catch (NumberFormatException e) {
                logger.error("Failed to parse amount: {}", amountStr);
                return null;
            }

        } else {
            logger.warn("No match found for SMS: {}", sms);
        }

        return null;
    }

    // Helper class
    public static class ParsedData {
        public double amount;
        public String merchant;

        public ParsedData(double amount, String merchant) {
            this.amount = amount;
            this.merchant = merchant;
        }
    }
}
