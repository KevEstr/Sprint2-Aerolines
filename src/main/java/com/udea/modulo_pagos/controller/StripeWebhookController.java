package com.udea.modulo_pagos.controller;

        import com.stripe.model.Event;
        import com.stripe.model.checkout.Session; // Importa la clase correcta
        import com.stripe.net.Webhook;
        import com.udea.modulo_pagos.graphql.InputPayment;
        import com.udea.modulo_pagos.service.IPaymentService;
        import com.udea.modulo_pagos.service.ITransactionService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
        import java.util.Map;

@RestController
public class StripeWebhookController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IPaymentService paymentService;

    private static final String endpointSecret = "whsec_b9c8b45f403fd69567d2553364f032accc5d81525c9887cbec6ed8d7d059a9ae";

    @PostMapping("/stripe-webhook")
    public Map<String, String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        String responseMessage = "";
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getData().getObject();

                Long transactionId = Long.parseLong(session.getMetadata().get("transactionId"));

                // Crear el pago en la base de datos
                InputPayment inputPayment = new InputPayment();
                inputPayment.setTransaction_id(transactionId);
                inputPayment.setGateway_payment_id(1L);  // ID de Stripe como gateway


                paymentService.createPayment(inputPayment);

                // Actualizar el estado de la transacción
                transactionService.updateTransactionStatus(transactionId, (byte) 1);

                responseMessage = "Pago completado y guardado exitosamente.";
            }

        } catch (Exception e) {
            responseMessage = "Error procesando el webhook: " + e.getMessage();
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", responseMessage);
        return response;
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment successful!");
    }

}

