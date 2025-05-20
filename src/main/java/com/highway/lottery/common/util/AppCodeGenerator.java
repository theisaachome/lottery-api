package com.highway.lottery.common.util;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String generateTicketCode(String agentCode){
        String datePart = LocalDate.now().format(DATE_FORMATTER);
        String randomPart = generateRandomString(6);
        return String.format("TICKET-AG%s-%s-%s", agentCode, datePart, randomPart);
    }

    public static String generateAgentCode(){
        String randomPart = generateRandomString(3);
        return String.format("AGENT%s", randomPart);
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateAgentCode());
    }

}
