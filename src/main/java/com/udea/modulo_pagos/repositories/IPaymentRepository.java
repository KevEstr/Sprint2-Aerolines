package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.Payment;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentRepository extends CrudRepository<Payment, Long> {


}
