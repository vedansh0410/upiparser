package com.example.upiparser.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

	 private static final String LOG_DIR = "src/main/resources/static/logs/";
	    private static final String LOG_FILE = LOG_DIR + "raw_sms_log.txt";
	public static void saveRawSMS(String sms) {
		// TODO Auto-generated method stub
		 try {
	            // Ensure directory exists
	            Files.createDirectories(Paths.get(LOG_DIR));

	            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
	                writer.write(sms + System.lineSeparator());
	            }

	        } catch (IOException e) {
	            throw new RuntimeException("Failed to save SMS log", e);
	        }
	}

}
