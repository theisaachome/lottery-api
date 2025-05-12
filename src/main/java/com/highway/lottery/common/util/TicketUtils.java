package com.highway.lottery.common.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TicketUtils {

    private static final String SECRET_KEY = "super-secret-key";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    public static String generateSignedQRPayload(TicketResponse ticket) throws Exception {
        // 1. Prepare signed QR payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("ticketCode", ticket.getTicketCode());
        payload.put("agentCode", ticket.getAgentCode());
        payload.put("createdAt", ticket.getCreatedAt());
//        payload.put("totalAmount", ticket.getTotalAmount());
//        payload.put("ticketNumbers", ticket.getTicketNumbers());

        String payloadJson =  objectMapper.writeValueAsString(payload);
        String signature = generateHmacSHA256(payloadJson,SECRET_KEY);

        Map<String, Object> qrData = new HashMap<>();
        qrData.put("payload", payload);
        qrData.put("signature", signature);

        return  objectMapper.writeValueAsString(qrData);

    }
    private static String generateHmacSHA256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes()));
    }

}
