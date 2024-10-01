package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.GatewayPayment;
import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputPayment;
import com.udea.modulo_pagos.repositories.IPaymentRepository;
import com.udea.modulo_pagos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IGatewayPaymentService gatewayPaymentService;

    @Autowired
    private IBookingService bookingService;

    @Override
    public Payment createPayment(InputPayment inputPayment) {
        GatewayPayment gatewayPayment = gatewayPaymentService.findGatewayPaymentById(inputPayment.getGateway_payment_id());
        Transaction transaction = transactionService.findTransactionById(inputPayment.getTransaction_id());

        // Solo permitir crear el pago si la transacción es "PENDING" (o algún otro estado válido)
        if (transaction.getStatus() == 1) {
            throw new IllegalStateException("No se puede realizar el pago nuevamente, el estado de la transacción es SUCCESS.");
        }

        // Crear el pago
        Payment payment = new Payment();
        payment.setTransaction(transaction);
        payment.setGatewayPayment(gatewayPayment);
        payment.setDate(LocalDate.now());


        // Actualizar el estado de la transacción solo si es necesario
        if (transaction.getStatus() != 1) {
            transactionService.updateTransactionStatus(inputPayment.getTransaction_id(), (byte) 1);
        }

        bookingService.updateBookingStatus(transaction.getBooking().getId(), true);

        // Guardar el pago
        paymentRepository.save(payment);

        return payment;
    }

    @Override
    public Payment findPaymentById(Long payment_id) {
        return paymentRepository.findById(payment_id).orElseThrow();
    }
}