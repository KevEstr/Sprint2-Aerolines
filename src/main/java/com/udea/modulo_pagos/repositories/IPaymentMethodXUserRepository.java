package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.PaymentMethodXUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPaymentMethodXUserRepository extends CrudRepository<PaymentMethodXUser, Long> {

    List<PaymentMethodXUser> findByUserId(Long userId);

}
