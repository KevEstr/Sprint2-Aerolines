package com.udea.modulo_pagos;

import com.stripe.exception.StripeException;
import com.udea.modulo_pagos.controller.*;
import com.udea.modulo_pagos.entities.GatewayPayment;
import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.PaymentMethodXUser;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.graphql.InputPayment;
import com.udea.modulo_pagos.service.*;
import com.udea.modulo_pagos.service.implementation.StripePaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ModuloPagosApplicationTests {

	@Autowired
	private GatewayPaymentController gatewayPaymentController;

	@Autowired
	private PaymentController paymentController;

	@Autowired
	private PaymentMethodController paymentMethodController;

	@Autowired
	private PaymentMethodXUserController paymentMethodXUserController;

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private StripeWebhookController stripeWebhookController;

	@MockBean
	private IGatewayPaymentService gatewayPaymentService;

	@MockBean
	private IPaymentService paymentService;

	@MockBean
	private IPaymentMethodService paymentMethodService;

	@MockBean
	private ITransactionService transactionService;

	@MockBean
	private StripePaymentService stripePaymentService;

	@MockBean
	private IPaymentMethodXUserService paymentMethodXUserService;

	@Test
	void contextLoads() {
		// Verifica que el contexto de la aplicación se carga correctamente.
		assertNotNull(gatewayPaymentController);
		assertNotNull(paymentController);
		assertNotNull(paymentMethodController);
		assertNotNull(paymentMethodXUserController);
		assertNotNull(transactionController);
	}

	@Test
	void testAllGatewayPayment() {
		// Configurar el comportamiento del mock
		when(gatewayPaymentService.allGatewayPayment()).thenReturn(Collections.emptyList());

		// Llamar al método del controlador
		List<GatewayPayment> result = gatewayPaymentController.allGatewayPayment();

		// Verificar el resultado
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void testCreatePayment() {
		InputPayment inputPayment = new InputPayment();
		inputPayment.setTransaction_id(1L);
		inputPayment.setGateway_payment_id(1L);

		Payment mockPayment = new Payment();  // Crea un pago simulado

		// Configurar el mock
		when(paymentService.createPayment(inputPayment)).thenReturn(mockPayment);

		// Llamar al método del controlador
		Payment result = paymentController.createPayment(inputPayment);

		// Verificar el resultado
		assertNotNull(result);
	}

	@Test
	void testCheckCard() {
		String cardNumber = "4111111111111111";
		String ccv = "123";
		String cardType = "VISA";
		String expiryDate = "12/2025";

		// Configurar el mock para devolver true
		when(paymentMethodService.checkCard(cardNumber, ccv, cardType, expiryDate)).thenReturn(true);

		// Llamar al método del controlador
		boolean result = paymentMethodController.checkCard(cardNumber, ccv, cardType, expiryDate);

		// Verificar el resultado
		assertTrue(result);
	}

	@Test
	void testFindPaymentById() {
		Long paymentId = 1L;
		Payment mockPayment = new Payment();  // Pago simulado

		// Configurar el mock
		when(paymentService.findPaymentById(paymentId)).thenReturn(mockPayment);

		// Llamar al método del controlador
		Payment result = paymentController.finById(paymentId);

		// Verificar el resultado
		assertNotNull(result);
	}

	@Test
	void testGetPaymentMethodsByUserId() {
		Long userId = 1L;

		// Configurar el mock
		when(paymentMethodXUserService.findAllByUserId(userId)).thenReturn(Collections.emptyList());

		// Llamar al método del controlador
		List<PaymentMethodXUser> result = paymentMethodXUserController.getPaymentMethodsByUserId(userId);

		// Verificar el resultado
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
