package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.repositories.IPaymentMethodRepository;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import com.udea.modulo_pagos.utils.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService {

    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;


    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod not found"));
    }

    public List<PaymentMethod> allPaymentMethods() {
        Iterable<PaymentMethod> iterable = paymentMethodRepository.findAll();
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        // Usar foreach para agregar los elementos a la lista
        iterable.forEach(paymentMethods::add);

        iterable.forEach(paymentMethod -> System.out.println(paymentMethod));


        return paymentMethods;
    }

    public boolean checkCard(String cardNumber, String ccv, String cardType, String expiryDate){

        return CreditCardValidator.isValidCard(cardNumber) && CreditCardValidator.isValidCCV(ccv, cardType) && CreditCardValidator.isValidExpiryDate(expiryDate);

    }
}
