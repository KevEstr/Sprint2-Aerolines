package com.udea.modulo_pagos.controller;

import com.stripe.exception.StripeException;
import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputTransaction;
import com.udea.modulo_pagos.service.IBookingService;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import com.udea.modulo_pagos.service.ITransactionService;
import com.udea.modulo_pagos.service.implementation.MercadoPagoService;
import com.udea.modulo_pagos.service.implementation.StripePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @Autowired
    private StripePaymentService stripePaymentService;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @SchemaMapping(typeName = "Transaction", field = "paymentMethod")
    public PaymentMethod getPaymentMethod(Transaction transaction) {
        return transaction.getPayment_method();
    }

    @SchemaMapping(typeName = "Transaction", field = "booking")
    public Booking getBooking(Transaction transaction) {
        return transaction.getBooking();
    }

    @MutationMapping
    public Transaction createTransaction(@Argument InputTransaction inputTransaction) {
        return transactionService.createTransaction(inputTransaction);
    }

    @MutationMapping
    public Transaction updateTransactionStatus(@Argument Long transactionId, @Argument byte newStatus) {
        return transactionService.updateTransactionStatus(transactionId, newStatus);
    }

    private static final float CONVERSION_RATE = 4000.0f; // 1 USD = 4000 COP

    @MutationMapping
    public String createStripePaymentSession(@Argument Long transactionId) {
        try {
            Transaction transaction = transactionService.findTransactionById(transactionId);
            float totalPriceInDollars = transaction.getTotal_price() / CONVERSION_RATE;
            long amountInCents = (long) (totalPriceInDollars * 100);
            return stripePaymentService.createCheckoutSession(transactionId, amountInCents);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }
    }

    @MutationMapping
    public String createMercadoPagoPreference(@Argument Long transactionId, @Argument Long amount) {
        return mercadoPagoService.createPaymentPreference(transactionId, amount);
    }
}
