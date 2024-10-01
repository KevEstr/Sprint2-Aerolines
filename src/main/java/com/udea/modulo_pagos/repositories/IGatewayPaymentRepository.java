package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.GatewayPayment;
import org.springframework.data.repository.CrudRepository;

public interface IGatewayPaymentRepository extends CrudRepository<GatewayPayment, Long> {
}
