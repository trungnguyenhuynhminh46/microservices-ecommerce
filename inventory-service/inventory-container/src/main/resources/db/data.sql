-- warehouse
INSERT INTO warehouse (id, name, postal_code, street, city, latitude, longitude, `description`, created_at, updated_at) VALUES
  (UUID_TO_BIN(UUID()), 'Warehouse 1', '12345', 'Street 1', 'City 1', 1.0, 1.0, 'Description 1', '2021-01-01', '2021-01-01'),
  (UUID_TO_BIN(UUID()), 'Warehouse 2', '12345', 'Street 2', 'City 2', 2.0, 2.0, 'Description 2', '2021-01-01', '2021-01-01'),
  (UUID_TO_BIN(UUID()), 'Warehouse 3', '12345', 'Street 3', 'City 3', 3.0, 3.0, 'Description 3', '2021-01-01', '2021-01-01');
