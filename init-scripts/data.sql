CREATE TABLE IF NOT EXISTS payment_method (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO payment_method (name) VALUES ('Credit Card');
INSERT INTO payment_method (name) VALUES ('PayPal');
INSERT INTO payment_method (name) VALUES ('Debit Card');

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY
);

INSERT INTO "user" (id) VALUES (1);
INSERT INTO "user" (id) VALUES (2);
INSERT INTO "user" (id) VALUES (3);

CREATE TABLE IF NOT EXISTS gateway_payment (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO gateway_payment (name) VALUES ('Stripe');
INSERT INTO gateway_payment (name) VALUES ('PayU');
INSERT INTO gateway_payment (name) VALUES ('Mercado Pago');

CREATE TABLE IF NOT EXISTS booking (
    id SERIAL PRIMARY KEY,
    is_paid BOOLEAN NOT NULL DEFAULT FALSE,
    price FLOAT NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

INSERT INTO booking (is_paid, price, user_id) VALUES (FALSE, 100.50, 1);
INSERT INTO booking (is_paid, price, user_id) VALUES (FALSE, 200.75, 2);
INSERT INTO booking (is_paid, price, user_id) VALUES (FALSE, 300.00, 3);


