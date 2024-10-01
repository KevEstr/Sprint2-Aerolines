package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputTransaction;

public interface ITransactionService {

    Transaction createTransaction(InputTransaction inputTransaction);

    Transaction updateTransactionStatus(Long id, byte status);

    Transaction findTransactionById(Long id);
}
