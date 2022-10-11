package com.thunv.ecommerceou.utils;

import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Utils {
    @Autowired
    private Environment env;
    @Autowired
    private CartService cartService;
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public Map<String,String> getAllErrorValidation(BindingResult result){
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
    }
    public DateFormat getDateFormatter(){

        return formatter;
    }
    public String randCodeConfirm(){
        double randomDouble = Math.random();
        randomDouble = randomDouble * 100000 + 1;
        return String.format("%06d",(int)randomDouble);
    }
    public String passwordEncoder(String password){
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }
    public String customMailForPayment(List<CartItem> cartItemList){
        String content = "";
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
                + String.format("<span class=\"price\" style=\"color: #000; font-size: 20px;\">Total: %,.0f VND</span>\n", this.cartService.getTotalPriceInCart(cartItemList.get(0).getCart()))
                + "                            </td>\n"
                + "                        </tr>";
        return content;
    }
}
