DROP DATABASE IF EXISTS order_service;
CREATE DATABASE order_service;
USE order_service;
-- order
CREATE TABLE t_order (
  id BINARY(16) NOT NULL,
   tracking_id VARCHAR(255) NULL,
   creator_id BINARY(16) NULL,
   final_price DECIMAL NULL,
   order_status VARCHAR(255) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_t_order PRIMARY KEY (id)
);

-- voucher
DROP TABLE IF EXISTS voucher CASCADE;
CREATE TABLE voucher (
  id BINARY(16) NOT NULL,
   code VARCHAR(255) NULL,
   discount_type SMALLINT NULL,
   discount_amount DECIMAL NULL,
   discount_percentage DECIMAL NULL,
   minimum_order_amount DECIMAL NULL,
   maximum_discount_amount DECIMAL NULL,
   expiry_date date NULL,
   remain INT NULL,
   active BIT(1) NULL,
   created_at date NULL,
   CONSTRAINT pk_voucher PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_voucher CASCADE;
CREATE TABLE order_voucher (
  order_id BINARY(16) NOT NULL,
   voucher_id BINARY(16) NOT NULL,
   CONSTRAINT pk_order_voucher PRIMARY KEY (order_id, voucher_id)
);

ALTER TABLE order_voucher ADD CONSTRAINT fk_ordvou_on_order_jpa_entity FOREIGN KEY (voucher_id) REFERENCES t_order (id);

ALTER TABLE order_voucher ADD CONSTRAINT fk_ordvou_on_voucher_jpa_entity FOREIGN KEY (order_id) REFERENCES voucher (id);

-- order item
DROP TABLE IF EXISTS order_item CASCADE;
CREATE TABLE order_item (
  id BIGINT NOT NULL,
   order_id BINARY(16) NOT NULL,
   product_id BINARY(16) NULL,
   sku VARCHAR(255) NULL,
   warehouse_id BINARY(16) NULL,
   quantity INT NULL,
   sub_total DECIMAL NULL,
   CONSTRAINT pk_order_item PRIMARY KEY (id, order_id)
);

ALTER TABLE order_item ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES t_order (id);
