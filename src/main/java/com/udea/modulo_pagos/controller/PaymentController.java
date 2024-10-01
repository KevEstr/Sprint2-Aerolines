package com.udea.modulo_pagos.controller;

import com.udea.modulo_pagos.entities.GatewayPayment;
import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputPayment;
import com.udea.modulo_pagos.service.IPaymentService;
import com.udea.modulo_pagos.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;


import java.io.IOException;

@Controller
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private ITransactionService transactionService;

    @SchemaMapping(typeName = "Payment", field = "transaction")
    public Transaction getTransaction(Payment payment) {
        return payment.getTransaction();
    }

    @SchemaMapping(typeName = "Payment", field = "gateway")
    public GatewayPayment getGateway(Payment payment) {
        return payment.getGatewayPayment();
    }


    @MutationMapping
    public Payment createPayment(@Argument InputPayment inputPayment) {
        return paymentService.createPayment(inputPayment);
    }

    @QueryMapping(name = "findPaymentById")
    public Payment finById(@Argument Long payment_id) {
        return paymentService.findPaymentById(payment_id);
    }
}





