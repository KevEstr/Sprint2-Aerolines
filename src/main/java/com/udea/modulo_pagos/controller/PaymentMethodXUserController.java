package com.udea.modulo_pagos.controller;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.entities.PaymentMethodXUser;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputPaymentMethodXUser;
import com.udea.modulo_pagos.graphql.InputTransaction;
import com.udea.modulo_pagos.service.IPaymentMethodXUserService;
import com.udea.modulo_pagos.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PaymentMethodXUserController {


    @Autowired
    private IPaymentMethodXUserService paymentMethodXUserService;


    @MutationMapping
    public PaymentMethodXUser createPaymentMethodXUser(@Argument InputPaymentMethodXUser inputPaymentMethodXUser) throws Exception {
        return paymentMethodXUserService.createPaymentMethodXUser(inputPaymentMethodXUser);
    }

    @MutationMapping
    public String deletePaymentMethodXUser (@Argument Long paymentMethodXUserId){

        try {
            paymentMethodXUserService.deletePaymentMethodXUser(paymentMethodXUserId);
            return "Payment method successfully deleted.";
        } catch (Exception e) {
            return "Error deleting payment method: " + e.getMessage();
        }
    }

    @QueryMapping
    public List<PaymentMethodXUser> getPaymentMethodsByUserId(@Argument Long userId) {
        return paymentMethodXUserService.findAllByUserId(userId);
    }

}
