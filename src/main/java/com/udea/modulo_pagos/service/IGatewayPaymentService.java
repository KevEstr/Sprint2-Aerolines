package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.GatewayPayment;
import com.udea.modulo_pagos.entities.PaymentMethod;

import java.util.List;

public interface IGatewayPaymentService {

    List<GatewayPayment> allGatewayPayment();
    GatewayPayment findGatewayPaymentById(Long id);

}
