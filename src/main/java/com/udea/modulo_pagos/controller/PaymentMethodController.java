package com.udea.modulo_pagos.controller;

import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @QueryMapping
    public List<PaymentMethod> allPaymentMethods() {
        return paymentMethodService.allPaymentMethods();
    }

    @MutationMapping
    public boolean checkCard(String cardNumber, String ccv, String cardType, String expiryDate){
        return paymentMethodService.checkCard(cardNumber, ccv, cardType, expiryDate);
    }
}
