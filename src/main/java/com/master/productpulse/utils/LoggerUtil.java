package com.master.productpulse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LoggerUtil {

    private static final Random random = new Random();

    public static synchronized String generateLoggerId() {
        long currentTimeMillis = System.currentTimeMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS"); // Adjust the format as needed
        String timestamp = dateFormat.format(new Date(currentTimeMillis));

        int randomInt = random.nextInt(10000); // Random integer with a length of 4

        return String.format("%04d", randomInt) + timestamp;
    }

}
