package com.thunv.ecommerceou.utils;

import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.ManageErrorLog;
import com.thunv.ecommerceou.services.CartService;
import com.thunv.ecommerceou.services.ManageErrorLogService;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Utils {
    @Autowired
    private Environment env;
    @Autowired
    private ManageErrorLogService manageErrorLogService;
    @Autowired
    private CartService cartService;
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public Map<String,String> getAllErrorValidation(BindingResult result){
        try {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) ->{

                String fieldName = ((FieldError) error).getField();
                String message = "";
                if (error.getDefaultMessage() != null){
                    message = error.getDefaultMessage();
                }else {
                    message = env.getProperty(error.getCode());
                }
                errors.put(fieldName, message);
            });
            return errors;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
    public DateFormat getDateFormatter(){

        return formatter;
    }
    public String randCodeConfirm(){
        try {
            double randomDouble = Math.random();
            randomDouble = randomDouble * 100000 + 1;
            return String.format("%06d",(int)randomDouble);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
    public String passwordEncoder(String password){
        try {
            return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }
    public List<Object[]> customListStatsMonth(List<Object[]> list) {
        try {
            boolean flag = false;
            if (list != null) {
                for (int i = 1; i <= 12; i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (i == (int) list.get(j)[0]) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        Object[] term = {i, 0};
                        list.add(term);
                    }
                    flag = false;
                }
                Collections.sort(list, (Object[] a1, Object[] a2) -> (int) a1[0] - (int) a2[0]);
            }
            return list;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;

    }

    public List<Object[]> customListStatsQuarter(List<Object[]> list) {
        try {
            boolean flag = false;
            if (list != null) {
                for (int i = 1; i <= 4; i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (i == (int) list.get(j)[0]) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        Object[] term = {i, 0};
                        list.add(term);
                    }
                    flag = false;
                }
                Collections.sort(list, (Object[] a1, Object[] a2) -> (int) a1[0] - (int) a2[0]);
            }
            return list;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public String customMailForPayment(List<CartItem> cartItemList, Double shipFee){
        try {
            String content = "";
            Double total = this.cartService.getTotalPriceInCart(cartItemList.get(0).getCart());
            for (CartItem cartItem: cartItemList) {
                content += "<tr style=\"border-bottom: 1px solid rgba(0,0,0,.05);\">\n"
                        + "                            <td valign=\"middle\" width=\"80%\" style=\"text-align:left; padding: 0 2.5em;\">\n"
                        + "                                <div class=\"product-entry\">\n"
                        + String.format("<img src=\"%s\" alt=\"\" style=\"width: 100px; max-width: 600px; height: auto; margin-bottom: 20px; display: block;\">\n", cartItem.getItemPost().getAvatar())
                        + "                                    <div class=\"text\">\n"
                        + String.format("<h4>%s</h4>\n", cartItem.getItemPost().getName())
                        + String.format("<span>%s</span>\n", cartItem.getItemPost().getDescription())
                        + String.format("<span>Qty:%d</span><br>\n", cartItem.getQuantity())
                        + String.format("<span>Unit price: %,.0f VND</span>\n", cartItem.getItemPost().getUnitPrice())
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </td>\n"
                        + "                            <td valign=\"middle\" width=\"20%\" style=\"text-align:left; padding: 0 2.5em;\">\n"
                        + String.format("<span class=\"price\" style=\"color: #000; font-size: 20px;\">%,.0f VND</span>\n", cartItem.getItemPost().getUnitPrice() * cartItem.getQuantity())
                        + "                            </td>\n"
                        + "                        </tr>";
            }
            content += "    <tr style=\"border-bottom: 1px solid rgba(0,0,0,.05);\">\n"
                    + "                            <td valign=\"middle\" width=\"20%\" style=\"text-align:left; padding: 0 2.5em;\">\n"
                    + String.format("<span class=\"price\" style=\"color: #000; font-size: 16px;\">Total: %,.0f VND + %,.0f VND (ship fee) = %,.0f VND</span>\n", total, shipFee, total+shipFee)
                    + "                            </td>\n"
                    + "                        </tr>";
            return content;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String hmacWithApacheCommons(String algorithm, String data, String key) {
        try {
            String hmac = new HmacUtils(algorithm, key).hmacHex(data);
            return hmac;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Boolean checkPhoneNumberIsValid(String phoneNumber){
        try {
            if (phoneNumber.startsWith("03") || phoneNumber.startsWith("05") || phoneNumber.startsWith("07")
                    || phoneNumber.startsWith("08") || phoneNumber.startsWith("09")){
                if (phoneNumber.length() == 10){
                    return true;
                }
            }
            return false;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return false;
    }

    public String generateUUID() throws RuntimeException{
        try {
            UUID uuid = UUID.randomUUID();
            return uuid.toString();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Boolean saveLogError(String logType, String logDetails) throws RuntimeException{
        try {
            ManageErrorLog manageErrorLog = new ManageErrorLog();
            manageErrorLog.setTypeLog(logType);
            manageErrorLog.setDetails(logDetails);
            this.manageErrorLogService.saveThirdPartyErrorLog(manageErrorLog);
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return false;
    }
}
