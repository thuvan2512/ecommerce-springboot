package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.ConfirmCode;
import com.thunv.ecommerceou.models.pojo.User;

public interface ConfirmCodeService {
    ConfirmCode createOrUpdateCode(User user);
    boolean sendConfirm(User user, String code);
}
