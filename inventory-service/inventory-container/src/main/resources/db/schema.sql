DROP DATABASE IF EXISTS inventory_service;
CREATE DATABASE inventory_service;
USE inventory_service;

-- warehouse
DROP TABLE IF EXISTS warehouse CASCADE;
CREATE TABLE warehouse (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   postal_code VARCHAR(255) NULL,
   street VARCHAR(255) NULL,
   city VARCHAR(255) NULL,
   latitude DOUBLE NULL,
   longitude DOUBLE NULL,
   `description` VARCHAR(255) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_warehouse PRIMARY KEY (id)
);

-- inventory
DROP TABLE IF EXISTS inventory CASCADE;
CREATE TABLE inventory (
  id BINARY(16) NOT NULL,
   product_id BINARY(16) NULL,
   sku VARCHAR(255) NULL,
   warehouse_id BINARY(16) NOT NULL,
   stock_quantity INT NULL,
   creator_id BINARY(16) NULL,
   updater_id BINARY(16) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_inventory PRIMARY KEY (id)
);

ALTER TABLE inventory ADD CONSTRAINT FK_INVENTORY_ON_WAREHOUSE FOREIGN KEY (warehouse_id) REFERENCES warehouse (id);

-- inventory_transaction
DROP TABLE IF EXISTS inventory_transaction CASCADE;
CREATE TABLE inventory_transaction (
  id BINARY(16) NOT NULL,
   product_id BINARY(16) NULL,
   sku VARCHAR(255) NULL,
   src_warehouse_id BINARY(16) NULL,
   dest_warehouse_id BINARY(16) NOT NULL,
   quantity INT NULL,
   creator_id BINARY(16) NULL,
   transaction_type VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   created_date date NULL,
   CONSTRAINT pk_inventory_transaction PRIMARY KEY (id)
);

ALTER TABLE inventory_transaction ADD CONSTRAINT FK_INVENTORY_TRANSACTION_ON_DEST_WAREHOUSE FOREIGN KEY (dest_warehouse_id) REFERENCES warehouse (id);

ALTER TABLE inventory_transaction ADD CONSTRAINT FK_INVENTORY_TRANSACTION_ON_SRC_WAREHOUSE FOREIGN KEY (src_warehouse_id) REFERENCES warehouse (id);

-- outbox
DROP TABLE IF EXISTS order_outbox CASCADE;
CREATE TABLE order_outbox (
  id BINARY(16) NOT NULL,
   saga_id BINARY(16) NULL,
   created_at date NULL,
   processed_at date NULL,
   type VARCHAR(255) NULL,
   payload VARCHAR(255) NULL,
   saga_status VARCHAR(255) NULL,
   outbox_status VARCHAR(255) NULL,
   order_inventory_confirmation_status VARCHAR(255) NULL,
   version INT NOT NULL,
   CONSTRAINT pk_order_outbox1 PRIMARY KEY (id)
);
