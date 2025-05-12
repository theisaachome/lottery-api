package com.highway.lottery.common.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TicketUtils {

    @Value("${app.base-url}")
    private String baseUrl;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY ;
    private  final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    public  String generateSignedQRPayload(TicketResponse ticket) throws Exception {
        // 1. Prepare signed QR payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("ticketCode", ticket.getTicketCode());
        payload.put("agentCode", ticket.getAgentCode());
        payload.put("createdAt", ticket.getCreatedAt());

        String payloadJson =  objectMapper.writeValueAsString(payload);
        String signature = generateHmacSHA256(payloadJson,SECRET_KEY);

        /*
        // ignore this code for the time being
        Map<String, Object> qrData = new HashMap<>();
        qrData.put("payload", payload);
        qrData.put("signature", signature);

        return  objectMapper.writeValueAsString(qrData);

         */
        String encodedSignature = Base64.getEncoder().encodeToString(signature.getBytes());
        String encodedPayload = Base64.getEncoder().encodeToString(payloadJson.getBytes());
        return baseUrl+"?signature="+encodedSignature+"&payload="+encodedPayload;

    }

    public boolean verifyTicket(String signature,String encodedPayload)throws Exception {
        // 1 Decode the payload,
        String jsonPayload =new String(Base64.getDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
        String decodedSignature = new String(Base64.getDecoder().decode(signature), StandardCharsets.UTF_8);
        // 2. Recalculate the signature using your existing method
        String expectedSignature = generateHmacSHA256(jsonPayload,SECRET_KEY);

        return expectedSignature.equals(decodedSignature);
    }
    private  String generateHmacSHA256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes()));
    }

}
