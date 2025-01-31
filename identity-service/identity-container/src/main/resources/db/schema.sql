DROP DATABASE IF EXISTS identity_service;
CREATE DATABASE identity_service;
USE identity_service;

-- permission
DROP TABLE IF EXISTS permission CASCADE;
CREATE TABLE permission (
  name VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NULL,
   CONSTRAINT pk_permission PRIMARY KEY (name)
);

-- role
DROP TABLE IF EXISTS `role` CASCADE;
CREATE TABLE `role` (
  name VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NULL,
   CONSTRAINT pk_role PRIMARY KEY (name)
);

-- role_permission
DROP TABLE IF EXISTS role_permission CASCADE;
CREATE TABLE role_permission (
  permission_id VARCHAR(255) NOT NULL,
   role_id VARCHAR(255) NOT NULL,
   CONSTRAINT pk_role_permission PRIMARY KEY (permission_id, role_id)
);

ALTER TABLE role_permission ADD CONSTRAINT fk_rolper_on_permission_jpa_entity FOREIGN KEY (permission_id) REFERENCES permission (name);

ALTER TABLE role_permission ADD CONSTRAINT fk_rolper_on_role_jpa_entity FOREIGN KEY (role_id) REFERENCES `role` (name);

-- user
DROP TABLE IF EXISTS user_account CASCADE;
CREATE TABLE user_account (
  id BINARY(16) NOT NULL,
   username VARCHAR(255) NULL,
   email VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   created_at date NULL,
   updated_at date NULL,
   CONSTRAINT pk_user_account PRIMARY KEY (id)
);

-- user_role
DROP TABLE IF EXISTS user_account_role CASCADE;
CREATE TABLE user_account_role (
  role_id VARCHAR(255) NOT NULL,
   user_account_id BINARY(16) NOT NULL,
   CONSTRAINT pk_user_account_role PRIMARY KEY (role_id, user_account_id)
);

ALTER TABLE user_account_role ADD CONSTRAINT fk_useaccrol_on_role_jpa_entity FOREIGN KEY (role_id) REFERENCES `role` (name);

ALTER TABLE user_account_role ADD CONSTRAINT fk_useaccrol_on_user_account_jpa_entity FOREIGN KEY (user_account_id) REFERENCES user_account (id);

-- revoked token
DROP TABLE IF EXISTS revoked_refresh_token CASCADE;
CREATE TABLE revoked_refresh_token (
  token VARCHAR(255) NOT NULL,
   user_id BINARY(16) NOT NULL,
   expires_in datetime NOT NULL,
   CONSTRAINT pk_revoked_refresh_token PRIMARY KEY (token)
);

ALTER TABLE revoked_refresh_token ADD CONSTRAINT FK_REVOKED_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user_account (id);
