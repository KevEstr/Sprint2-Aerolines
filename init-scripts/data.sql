-- Crear métodos de pago
INSERT INTO payment_method (id, name) VALUES (1, 'Credit Card');
INSERT INTO payment_method (id, name) VALUES (2, 'PayPal');

-- Crear gateways de pago
INSERT INTO gateway_payment (id, name) VALUES (1, 'Stripe');
INSERT INTO gateway_payment (id, name) VALUES (2, 'PayU');
