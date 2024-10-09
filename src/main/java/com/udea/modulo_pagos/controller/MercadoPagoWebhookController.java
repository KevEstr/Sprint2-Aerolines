package com.udea.modulo_pagos.controller;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.udea.modulo_pagos.graphql.InputPayment;
import com.udea.modulo_pagos.service.IPaymentService;
import com.udea.modulo_pagos.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MercadoPagoWebhookController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/mercado-pago-webhook")
    public Map<String, String> handleMercadoPagoWebhook(@RequestBody Map<String, Object> payload) {
        String responseMessage = "";
        try {
            System.out.println("Payload recibido: " + payload);

            if (payload.containsKey("data") && payload.containsKey("type")) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String eventType = (String) payload.get("type");

                if (data.containsKey("id")) {
                    Long mercadoPagoPaymentId = Long.valueOf(data.get("id").toString());

                    // Utilizar PaymentClient en lugar de PreferenceClient
                    PaymentClient paymentClient = new PaymentClient();
                    Payment payment = paymentClient.get(mercadoPagoPaymentId);

                    // Recuperar el transactionId desde los metadatos
                    Map<String, Object> metadata = payment.getMetadata();
                    String transactionIdString = (String) metadata.get("transactionId");

                    if (transactionIdString != null) {
                        Long transactionId = Long.valueOf(transactionIdString);
                        System.out.println("Transacción ID: " + transactionId);

                        switch (eventType) {
                            case "payment":
                                InputPayment inputPayment = new InputPayment();
                                inputPayment.setTransaction_id(transactionId);
                                inputPayment.setGateway_payment_id(2L);  // ID de MercadoPago como gateway
                                paymentService.createPayment(inputPayment);

                                transactionService.updateTransactionStatus(transactionId, (byte) 1);
                                responseMessage = "Pago completado y guardado exitosamente.";
                                break;

                            default:
                                responseMessage = "Evento no manejado: " + eventType;
                        }
                    } else {
                        responseMessage = "TransactionId no encontrado en los metadatos.";
                    }
                } else {
                    responseMessage = "ID de pago no encontrado en el payload.";
                }
            } else {
                responseMessage = "Payload no contiene los campos requeridos.";
            }
        } catch (Exception e) {
            responseMessage = "Error procesando el webhook de Mercado Pago: " + e.getMessage();
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", responseMessage);
        return response;
    }

}
