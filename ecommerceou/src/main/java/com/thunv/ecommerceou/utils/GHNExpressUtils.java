package com.thunv.ecommerceou.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GHNExpressUtils {

    @Autowired
    private Environment env;
    @Autowired
    private Utils utils;
    private static final String GHN_EXPRESS_LINK_GET_PROVINCES = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private static final String GHN_EXPRESS_LINK_GET_DISTRICTS = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private static final String GHN_EXPRESS_LINK_GET_WARDS = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
    private static final String GHN_EXPRESS_LINK_GET_AVAILABLE_SERVICES = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
    private static final String GHN_EXPRESS_LINK_CALCULATE_THE_SHIP_FEE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String GHN_EXPRESS_LINK_CALCULATE_THE_EXPECTED_DELIVERY_TIME= "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/leadtime";
    private static final String GHN_EXPRESS_LINK_CREATE_ORDER = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";

    public Map<Object, Object> getLocationProvinceOfGHNExpress() throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.production");

        Map<Object, Object> mapResult = new HashMap<>();
        try {
            HttpResponse response = Request.Get(GHN_EXPRESS_LINK_GET_PROVINCES)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Token", token)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
//            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult  = mapper.readValue(temp, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }

    public Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID) throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.production");

        Map<Object, Object> mapResult = new HashMap<>();
        try {

            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_GET_DISTRICTS)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Token", token)
                    .bodyString("{\n" +
                            String.format("    \"province_id\": %d", provinceID) +
                            "\n}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
//            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult  = mapper.readValue(temp, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }

    public Map<Object, Object> getLocationWardsOfGHNExpress(int districtID) throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.production");

        Map<Object, Object> mapResult = new HashMap<>();
        try {

            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_GET_WARDS)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Token", token)
                    .bodyString("{\n" +
                            String.format("    \"district_id\": %d", districtID) +
                            "\n}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
//            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult = mapper.readValue(temp, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }

    public Map<Object, Object> getAvailableServiceShippingOfGHNExpress(int fromDistrictID, String fromWatd, int toDistrictID, String toWardID) throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.sandbox");
        String shopID = env.getProperty("ghn.shopManager.shopID");

        Map<Object, Object> mapResult = new HashMap<>();
        try {

            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_GET_AVAILABLE_SERVICES)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Token", token)
                    .bodyString("{\n" +
                            String.format("    \"shop_id\": %d, \n", Integer.parseInt(shopID)) +
                            String.format("    \"from_district\": %d, \n", fromDistrictID) +
                            String.format("    \"to_district\": %d \n", toDistrictID) +
                            "\n}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
//            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult = mapper.readValue(temp, Map.class);

            if (String.valueOf(mapResult.get("code")).equals("200")){
                try {
                    List<Map<Object, Object>> obj = (List<Map<Object, Object>>) mapResult.get("data");
                    if (obj.size() > 0){
                        for (Map<Object, Object> item: obj){
                            Map<Object, Object> getShipFee = calculatorShipFeeOfGHNExpress(
                                    fromDistrictID,
                                    toDistrictID,
                                    toWardID,
                                    Integer.parseInt(item.get("service_id").toString()),
                                    Integer.parseInt(item.get("service_type_id").toString()));
                            Map<String, Object> shipFeeInfo = new HashMap<>();
                            if (String.valueOf(getShipFee.get("code")).equals("200")){
                                Map<Object, Object> shipFeeInfoTemp= (Map<Object,Object>) getShipFee.get("data");
                                shipFeeInfo.put("isSuccess", 1);
                                shipFeeInfo.put("detail", "Calculate the ship fee successfully !!!");
                                shipFeeInfo.put("total", shipFeeInfoTemp.get("total"));
                                shipFeeInfo.put("shipFee", shipFeeInfoTemp.get("service_fee"));
                            }else {
                                shipFeeInfo.put("isSuccess", 0);
                                shipFeeInfo.put("detail", getShipFee.get("message"));
                                shipFeeInfo.put("total", null);
                                shipFeeInfo.put("shipFee", null);
                            }
                            item.put("shipFeeService", shipFeeInfo);

                            Map<Object, Object> getExpectedTime = calculatorExpectedDeliveryTimeOfGHNExpress(
                                    fromDistrictID,
                                    fromWatd,
                                    toDistrictID,
                                    toWardID,
                                    Integer.parseInt(item.get("service_id").toString()));
                            Map<String, Object> expectedTime = new HashMap<>();
                            if (String.valueOf(getExpectedTime.get("code")).equals("200")){
                                Map<Object, Object> expectedTimeTemp= (Map<Object,Object>) getExpectedTime.get("data");
                                expectedTime.put("isSuccess", 1);
                                expectedTime.put("detail", "Calculate the expected delivery time successfully !!!");
                                expectedTime.put("leadTime", expectedTimeTemp.get("leadtime"));
                                expectedTime.put("orderDate", expectedTimeTemp.get("order_date"));
                            }else {
                                expectedTime.put("isSuccess", 0);
                                expectedTime.put("detail", getExpectedTime.get("message"));
                                expectedTime.put("leadTime", null);
                                expectedTime.put("orderDate", null);
                            }
                            item.put("expectedDeliveryTime", expectedTime);
                        }
                    }
                }catch (Exception e){
                    System.out.printf("%s", e.toString());
                }
            }
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }

    public Map<Object, Object> calculatorShipFeeOfGHNExpress(int fromDistrictID, int toDistrictID, String toWardID, int serviceID, int serviceTypeID) throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.sandbox");
        String shopID = env.getProperty("ghn.shopManager.shopID");

        Map<Object, Object> mapResult = new HashMap<>();
        try {

            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_CALCULATE_THE_SHIP_FEE)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Content-Type", "text/plain")
                    .addHeader("ShopId", shopID)
                    .addHeader("Token", token)
                    .bodyString("{\n" +
                            String.format("    \"from_district_id\": %d, \n", fromDistrictID) +
                            String.format("    \"to_district_id\": %d, \n", toDistrictID) +
                            String.format("    \"to_ward_code\": \"%s\", \n", toWardID) +
                            String.format("    \"height\": %d, \n", 0) +
                            String.format("    \"length\": %d, \n", 0) +
                            String.format("    \"weight\": %d, \n", 100) +
                            String.format("    \"width\": %d, \n", 0) +
                            String.format("    \"insurance_value\": %d, \n", 0) +
                            String.format("    \"coupon\": %s, \n", "null") +
                            String.format("    \"service_id\": %d, \n", serviceID) +
                            String.format("    \"service_type_id\": %d, \n", serviceTypeID) +
                            String.format("    \"cod_value\": %d \n", 0) +
                            "\n}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult = mapper.readValue(temp, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }


    public Map<Object, Object> calculatorExpectedDeliveryTimeOfGHNExpress(int fromDistrictID, String fromWardID, int toDistrictID, String toWardID, int serviceID) throws ClientProtocolException, IOException {
        String token = env.getProperty("ghn.token.sandbox");
        String shopID = env.getProperty("ghn.shopManager.shopID");

        Map<Object, Object> mapResult = new HashMap<>();
        try {
            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_CALCULATE_THE_EXPECTED_DELIVERY_TIME)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("ShopId", shopID)
                    .addHeader("Token", token)
                    .bodyString("{\n" +
                            String.format("    \"from_district_id\": %d, \n", fromDistrictID) +
                            String.format("    \"to_district_id\": %d, \n", toDistrictID) +
                            String.format("    \"to_ward_code\": \"%s\", \n", toWardID) +
                            String.format("    \"from_ward_code\": \"%s\", \n", fromWardID) +
                            String.format("    \"service_id\": %d \n", serviceID) +
                            "\n}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
            System.out.println(temp);
            ObjectMapper mapper = new ObjectMapper();
            mapResult = mapper.readValue(temp, Map.class);
            return mapResult;
        }catch (Exception exception){
            System.out.printf("%s", exception.toString());
        }
        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
        return mapResult;
    }

//    public Map<Object, Object> createOrderShipping(Agency agency, CustomerAddressBook customerAddressBook) throws ClientProtocolException, IOException {
//        String token = env.getProperty("ghn.token.sandbox");
//        String shopID = env.getProperty("ghn.shopManager.shopID");
//        String requireNote = env.getProperty("ghn.shopManager.required_note");
//
//        Map<Object, Object> mapResult = new HashMap<>();
//        try {
//
//            HttpResponse response = Request.Post(GHN_EXPRESS_LINK_CALCULATOR_SHIP_FEE)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("ShopId", shopID)
//                    .addHeader("Token", token)
//                    .bodyString("{\n" +
//                            String.format("    \"payment_type_id\": %d, \n", 2) +
//                            String.format("    \"note\": %s, \n", "Please call before delivery") +
//                            String.format("    \"from_name\": %s, \n", agency.getName()) +
//                            String.format("    \"from_phone\": %s, \n", agency.getHotline()) +
//                            String.format("    \"from_address\": %s, \n", agency.getFromAddress()) +
//                            String.format("    \"from_ward_name\": %s, \n", agency.getFromWardName()) +
//                            String.format("    \"from_district_name\": %s, \n", agency.getFromDistrictName()) +
//                            String.format("    \"from_province_name\": %s, \n", agency.getFromProvinceName()) +
//                            String.format("    \"required_note\": %s, \n", requireNote) +
//                            String.format("    \"return_name\": %s, \n", agency.getName()) +
//                            String.format("    \"return_phone\": %s, \n", agency.getHotline()) +
//                            String.format("    \"return_address\": %s, \n", agency.getFromAddress()) +
//                            String.format("    \"return_ward_name\": %s, \n", agency.getFromWardName()) +
//                            String.format("    \"return_district_name\": %s, \n", agency.getFromDistrictName()) +
//                            String.format("    \"return_province_name\": %s, \n", agency.getFromProvinceName()) +
//                            String.format("    \"client_order_code\": %s, \n", "") +
//                            String.format("    \"to_name\": %s, \n", customerAddressBook.getCustomer().getUsername()) +
//                            String.format("    \"to_phone\": %s, \n", customerAddressBook.getDeliveryPhone()) +
//                            String.format("    \"to_address\": %s, \n", customerAddressBook.getToAddress()) +
//                            String.format("    \"to_ward_name\": %s, \n", customerAddressBook.getToWardName()) +
//                            String.format("    \"to_district_name\": %s, \n", customerAddressBook.getToDistrictName()) +
//                            String.format("    \"to_province_name\": %s, \n", customerAddressBook.getToProvinceName()) +
//                            String.format("    \"cod_amount\": %d, \n", fromD) +
//                            String.format("    \"content\": %s, \n", "Thank you for shopping at OU Ecom.") +
//                            String.format("    \"weight\": %d, \n", 1) +
//                            String.format("    \"length\": %d, \n", 0) +
//                            String.format("    \"width\": %d, \n", 0) +
//                            String.format("    \"height\": %d, \n", 0) +
//                            String.format("    \"cod_failed_amount\": %d, \n", 0) +
//                            String.format("    \"pick_station_id\": %d, \n", fromDistrictID) +
//                            String.format("    \"deliver_station_id\": %d, \n", fromDistrictID) +
//                            String.format("    \"insurance_value\": %d, \n", fromDistrictID) +
//                            String.format("    \"service_id\": %d, \n", fromDistrictID) +
//                            String.format("    \"service_type_id\": %d, \n", fromDistrictID) +
//                            String.format("    \"coupon\": %d, \n", fromDistrictID) +
//                            String.format("    \"pick_shift\": %d, \n", fromDistrictID) +
//                            String.format("    \"pickup_time\": %d, \n", fromDistrictID) +
//                            String.format("    \"items\": %s \n", fromDistrictID) +
//                            "\n}", ContentType.APPLICATION_JSON)
//                    .execute().returnResponse();
//            String temp = new String(EntityUtils.toByteArray(response.getEntity()));
//            System.out.println(temp);
//            ObjectMapper mapper = new ObjectMapper();
//            mapResult = mapper.readValue(temp, Map.class);
//            return mapResult;
//        }catch (Exception exception){
//            System.out.printf("%s", exception.toString());
//        }
//        mapResult.put("message_err", "Có lỗi trong quá khi call api của GHN !!!");
//        return mapResult;
//    }
}
