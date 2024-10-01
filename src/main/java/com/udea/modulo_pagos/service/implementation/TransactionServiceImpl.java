package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputTransaction;
import com.udea.modulo_pagos.repositories.ITransactionRepository;
import com.udea.modulo_pagos.service.IBookingService;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import com.udea.modulo_pagos.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final float TAXES = 0.19f;

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    public Transaction createTransaction(InputTransaction inputTransaction){
        Transaction transaction = new Transaction();
        transaction.setStatus((byte) 0);
        transaction.setDate(LocalDate.now());
        transaction.setAdditional_charge(inputTransaction.getAdditional_charge());

        Booking booking = bookingService.getBookingById(inputTransaction.getBooking());
        transaction.setBooking(booking);

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(inputTransaction.getPayment_method());
        transaction.setPayment_method(paymentMethod);

        float subtotal = booking.getPrice() + inputTransaction.getAdditional_charge();
        float totalPrice = subtotal + (subtotal * TAXES);
        transaction.setTotal_price(totalPrice);

        transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction updateTransactionStatus(Long transactionId, byte newStatus){
        var transaction= new Transaction ();
        transaction= transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus(newStatus);
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }


}
