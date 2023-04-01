package com.thunv.ecommerceou.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TwilioSendSMSUtils {
    @Autowired
    private Environment env;

    public void sendSMSUsingTwilio(String toPhone, String content){
        try {
            String accountSID = this.env.getProperty("twilio.account_sid");
            String authToken = this.env.getProperty("twilio.auth_token");
            String fromPhone = this.env.getProperty("twilio.phone_number");
            Twilio.init(accountSID, authToken);
            Message message = Message.creator(
                            new PhoneNumber(toPhone),
                            new PhoneNumber(fromPhone),
                            content).create();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
