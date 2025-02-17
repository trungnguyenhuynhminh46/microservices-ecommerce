DROP DATABASE IF EXISTS product_service;
CREATE DATABASE product_service;
USE product_service;

-- category
DROP TABLE IF EXISTS category CASCADE;
CREATE TABLE category (
  id BINARY(16) NOT NULL,
   code VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category ADD CONSTRAINT uc_category_code UNIQUE (code);

-- product
DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE product (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   price DECIMAL NULL,
   `description` VARCHAR(255) NULL,
   tags VARCHAR(255) NULL,
   rating FLOAT NULL,
   category_id BINARY(16) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

-- attribute
DROP TABLE IF EXISTS p_attribute CASCADE;
CREATE TABLE p_attribute (
  id BIGINT NOT NULL,
   product_id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   default_value VARCHAR(255) NULL,
   options VARCHAR(255) NULL,
   CONSTRAINT pk_p_attribute PRIMARY KEY (id, product_id)
);

ALTER TABLE p_attribute ADD CONSTRAINT FK_P_ATTRIBUTE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);


-- template product
DROP TABLE IF EXISTS template_product CASCADE;
CREATE TABLE template_product (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   price DECIMAL NULL,
   `description` VARCHAR(255) NULL,
   tags VARCHAR(255) NULL,
   rating FLOAT NULL,
   category_id BINARY(16) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_template_product PRIMARY KEY (id)
);

-- template attribute
DROP TABLE IF EXISTS template_attribute CASCADE;
CREATE TABLE template_attribute (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   default_value VARCHAR(255) NULL,
   options VARCHAR(255) NULL,
   CONSTRAINT pk_template_attribute PRIMARY KEY (id)
);

DROP TABLE IF EXISTS template_product_template_attribute CASCADE;
CREATE TABLE template_product_template_attribute (
  template_attribute_id BINARY(16) NOT NULL,
   template_product_id BINARY(16) NOT NULL,
   CONSTRAINT pk_template_product_template_attribute PRIMARY KEY (template_attribute_id, template_product_id)
);

ALTER TABLE template_product_template_attribute ADD CONSTRAINT fk_temprotematt_on_template_attribute_jpa_entity FOREIGN KEY (template_attribute_id) REFERENCES template_attribute (id);

ALTER TABLE template_product_template_attribute ADD CONSTRAINT fk_temprotematt_on_template_product_jpa_entity FOREIGN KEY (template_product_id) REFERENCES template_product (id);

