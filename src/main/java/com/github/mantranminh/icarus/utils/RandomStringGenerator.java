package com.github.mantranminh.icarus.utils;

import java.util.Random;

public class RandomStringGenerator {
    private static final String DIGITS = "0123456789";

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(DIGITS.length());
            char randomDigit = DIGITS.charAt(randomIndex);
            sb.append(randomDigit);
        }

        return sb.toString();
    }
}
