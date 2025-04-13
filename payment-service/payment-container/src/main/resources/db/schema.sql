DROP DATABASE IF EXISTS payment_service;
CREATE DATABASE payment_service;
USE payment_service;

-- credit entry table
DROP TABLE IF EXISTS credit_entry;
CREATE TABLE credit_entry (
  id BINARY(16) NOT NULL,
   customer_id BINARY(16) NULL,
   total_credit_amount DECIMAL NULL,
   CONSTRAINT pk_credit_entry PRIMARY KEY (id)
);

-- credit history table
DROP TABLE IF EXISTS credit_history;
CREATE TABLE credit_history (
  id BINARY(16) NOT NULL,
   customer_id BINARY(16) NULL,
   amount DECIMAL NULL,
   transaction_type VARCHAR(255) NULL,
   created_at date NULL,
   CONSTRAINT pk_credit_history PRIMARY KEY (id)
);

-- payment table
DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
  id BINARY(16) NOT NULL,
   order_id BINARY(16) NULL,
   customer_id BINARY(16) NULL,
   total_price DECIMAL NULL,
   created_at date NULL,
   payment_status VARCHAR(255) NULL,
   CONSTRAINT pk_payment PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_outbox;
CREATE TABLE order_outbox (
  id BINARY(16) NOT NULL,
   saga_id BINARY(16) NULL,
   created_at date NULL,
   processed_at date NULL,
   type VARCHAR(255) NULL,
   payload VARCHAR(255) NULL,
   saga_status VARCHAR(255) NULL,
   payment_status VARCHAR(255) NULL,
   outbox_status VARCHAR(255) NULL,
   version INT NOT NULL,
   CONSTRAINT pk_order_outbox PRIMARY KEY (id)
);
