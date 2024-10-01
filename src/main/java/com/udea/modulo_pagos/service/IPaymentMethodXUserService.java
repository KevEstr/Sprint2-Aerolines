package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.PaymentMethodXUser;
import com.udea.modulo_pagos.graphql.InputPaymentMethodXUser;

import java.util.List;

public interface IPaymentMethodXUserService {

    PaymentMethodXUser createPaymentMethodXUser(InputPaymentMethodXUser inputPaymentMethodXUser) throws Exception;
    List<PaymentMethodXUser> findAllByUserId(Long userId);
    void deletePaymentMethodXUser (Long paymentMethodXUserId) throws Exception;
}
