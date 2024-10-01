package com.udea.modulo_pagos.service.implementation;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    public String createCheckoutSession(Long transactionId, Long amount) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        // Configurar los parámetros de la sesión
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setSuccessUrl("http://localhost:8081/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8081/cancel")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(amount) // Cantidad en centavos
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Transaction ID: " + transactionId)
                                                                        .build())
                                                        .build())
                                        .setQuantity(1L)
                                        .build())
                        .putMetadata("transactionId", transactionId.toString()) // Agregar el transactionId como metadata
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .setCaptureMethod(SessionCreateParams.PaymentIntentData.CaptureMethod.AUTOMATIC) // Captura automática
                                        .build())
                        .build();

        // Crear la sesión de Stripe
        Session session = Session.create(params);
        return session.getUrl(); // Retornar la URL de Checkout
    }
}