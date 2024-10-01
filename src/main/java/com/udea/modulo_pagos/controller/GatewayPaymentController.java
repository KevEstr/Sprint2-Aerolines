package com.udea.modulo_pagos.controller;

import com.udea.modulo_pagos.entities.GatewayPayment;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.service.IGatewayPaymentService;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GatewayPaymentController {

    @Autowired
    private IGatewayPaymentService gatewayPaymentService;

    @QueryMapping
    public List<GatewayPayment> allGatewayPayment() {
        return gatewayPaymentService.allGatewayPayment();
    }

}
