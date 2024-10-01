package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.graphql.InputPayment;

public interface IPaymentService {

    Payment createPayment (InputPayment inputPayment);

    Payment findPaymentById(Long payment_id);
}
