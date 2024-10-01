package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
}
