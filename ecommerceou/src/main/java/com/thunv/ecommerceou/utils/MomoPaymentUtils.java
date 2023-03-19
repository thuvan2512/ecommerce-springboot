package com.thunv.ecommerceou.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MomoPaymentUtils {
    @Autowired
    private Environment env;
    @Autowired
    private Utils utils;
    private static final String MOMO_LINK_GET_PAYMENT_INFO = "https://test-payment.momo.vn/v2/gateway/api/create";

    public Map<String, String> getPaymentInfo(String orderId, String amount, String orderInfo, String requestId) throws ClientProtocolException, IOException {
        String partnerCode = env.getProperty("momo.partnerCode");
        String partnerName = env.getProperty("momo.partnerName");
        String storeId = env.getProperty("momo.storeId");
        String requestType = env.getProperty("momo.requestType");
        String ipnUrl =  env.getProperty("momo.ipnUrl");
        String redirectUrl =  env.getProperty("momo.redirectUrl");
        String accessKey = env.getProperty("momo.accessKey");
        String secretKey = env.getProperty("momo.secretKey");
        String rawCode = String.format("accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                accessKey, amount, "", ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType);
        String signature = this.utils.hmacWithApacheCommons("HmacSHA256", rawCode, secretKey);
        Map<String, String> mapResult = new HashMap<>();
        try {
            String response = Request.Post(MOMO_LINK_GET_PAYMENT_INFO)
                    .addHeader("Content-Type", "application/json")
                    .bodyString("{\n" +
                            String.format("    \"partnerCode\": \"%s\",\n", partnerCode) +
                            String.format("    \"partnerName\": \"%s\",\n", partnerName) +
                            String.format("    \"storeId\": \"%s\",\n", storeId) +
                            String.format("    \"requestType\": \"%s\",\n", requestType) +
                            String.format("    \"ipnUrl\": \"%s\",\n", ipnUrl) +
                            String.format("    \"redirectUrl\": \"%s\",\n", redirectUrl) +
                            String.format("    \"orderId\": \"%s\",\n", orderId) +
                            String.format("    \"amount\": \"%s\",\n", amount) +
                            "    \"lang\": \"vi\",\n" +
                            String.format("    \"orderInfo\": \"%s\",\n", orderInfo) +
                            String.format("    \"requestId\": \"%s\",\n", requestId) +
                            "    \"extraData\": \"\",\n" +
                            String.format("    \"signature\": \"%s\"\n", signature) +
                            "}", ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            ObjectMapper mapper = new ObjectMapper();
            mapResult = mapper.readValue(response, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
            mapResult.put("payUrl", null);
            mapResult.put("resultCode", "1");
            mapResult.put("message", String.format("Fail: %s", exception.toString()));
        }
        return mapResult;
    }
}
