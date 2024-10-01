package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface ITransactionRepository extends CrudRepository <Transaction, Long> {
}
