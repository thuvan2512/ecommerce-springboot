package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.PaymentType;

import java.util.List;

public interface PaymentTypeService {
    PaymentType getPaymentTypeByID(int typeID);
    List<PaymentType> getAllPaymentType();
    PaymentType createPaymentType(PaymentType paymentType);
    PaymentType updatePaymentType(PaymentType paymentType);
    boolean deletePaymentType(int typeID);
}
