-- category data
INSERT INTO category (id, code, name, description, created_at, updated_at) VALUES
  (UUID_TO_BIN(UUID()), 'CAT001', 'Electronics', 'Electronic items', NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'CAT002', 'Books', 'Books and literature', NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'CAT003', 'Clothing', 'Apparel and accessories', NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'CAT004', 'Home & Kitchen', 'Furniture and appliances', NOW(), NOW());

-- product data
INSERT INTO product (id, name, price, description, tags, rating, category_id, created_at, updated_at) VALUES
  (UUID_TO_BIN(UUID()), 'Smartphone', 699.99, 'Latest model smartphone', 'electronics,phone', 4.5, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Laptop', 999.99, 'High performance laptop', 'electronics,computer', 4.7, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Tablet', 499.99, 'Portable tablet for work and play', 'electronics,tablet', 4.6, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Smartwatch', 199.99, 'Wearable smartwatch for fitness', 'electronics,watch', 4.3, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Novel Book', 15.99, 'Fiction novel', 'books,fiction', 4.2, (SELECT id FROM category WHERE code = 'CAT002'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Textbook', 49.99, 'Educational textbook for students', 'books,education', 4.7, (SELECT id FROM category WHERE code = 'CAT002'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'E-book', 9.99, 'Digital book for e-readers', 'books,ebook', 4.4, (SELECT id FROM category WHERE code = 'CAT002'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Blender', 49.99, 'High-speed blender', 'home,appliance', 4.3, (SELECT id FROM category WHERE code = 'CAT004'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Microwave', 79.99, 'Compact microwave oven', 'home,appliance', 4.6, (SELECT id FROM category WHERE code = 'CAT004'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Refrigerator', 499.99, 'Energy efficient refrigerator', 'home,appliance', 4.8, (SELECT id FROM category WHERE code = 'CAT004'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'T-shirt', 19.99, 'Cotton T-shirt', 'clothing,apparel', 4.0, (SELECT id FROM category WHERE code = 'CAT003'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Jeans', 39.99, 'Comfortable denim jeans', 'clothing,apparel', 4.2, (SELECT id FROM category WHERE code = 'CAT003'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Jacket', 79.99, 'Warm winter jacket', 'clothing,apparel', 4.5, (SELECT id FROM category WHERE code = 'CAT003'), NOW(), NOW());

-- attribute data
INSERT INTO p_attribute (id, product_id, name, default_value, options) VALUES
  (1, (SELECT id FROM product WHERE name = 'Smartphone'), 'Size', 'Small',
    '[ { "name": "Small", "changeAmount": 0 }, { "name": "Large", "changeAmount": 200 } ]'),
  (2, (SELECT id FROM product WHERE name = 'Smartphone'), 'Color', 'Black',
    '[ { "name": "Black", "changeAmount": 0 }, { "name": "White", "changeAmount": 50 }, { "name": "Blue", "changeAmount": 100 } ]'),
  (3, (SELECT id FROM product WHERE name = 'Smartphone'), 'RAM', '4GB',
    '[ { "name": "4GB", "changeAmount": 0 }, { "name": "8GB", "changeAmount": 150 }, { "name": "16GB", "changeAmount": 300 } ]'),
  (4, (SELECT id FROM product WHERE name = 'Smartphone'), 'Version', 'Standard',
    '[ { "name": "Standard", "changeAmount": 0 }, { "name": "Pro", "changeAmount": 400 }, { "name": "Ultra", "changeAmount": 700 } ]'),
  (5, (SELECT id FROM product WHERE name = 'Laptop'), 'Size', '13-inch', "[ { \"name\": \"13-inch\", \"changeAmount\": 0 }, { \"name\": \"15-inch\", \"changeAmount\": 200 } ]"),
  (6, (SELECT id FROM product WHERE name = 'Tablet'), 'Size', '8-inch', "[ { \"name\": \"8-inch\", \"changeAmount\": 0 }, { \"name\": \"10-inch\", \"changeAmount\": 100 } ]"),
  (7, (SELECT id FROM product WHERE name = 'Smartwatch'), 'Color', 'Black', "[ { \"name\": \"Black\", \"changeAmount\": 0 }, { \"name\": \"Silver\", \"changeAmount\": 50 } ]"),
  (8, (SELECT id FROM product WHERE name = 'Novel Book'), 'Format', 'Paperback', "[ { \"name\": \"Paperback\", \"changeAmount\": 0 }, { \"name\": \"Hardcover\", \"changeAmount\": 5 } ]"),
  (9, (SELECT id FROM product WHERE name = 'Textbook'), 'Edition', '1st', "[ { \"name\": \"1st\", \"changeAmount\": 0 }, { \"name\": \"2nd\", \"changeAmount\": 10 } ]"),
  (10, (SELECT id FROM product WHERE name = 'E-book'), 'File Type', 'PDF', "[ { \"name\": \"PDF\", \"changeAmount\": 0 }, { \"name\": \"EPUB\", \"changeAmount\": 0 } ]"),
  (11, (SELECT id FROM product WHERE name = 'Blender'), 'Color', 'Black', "[ { \"name\": \"Black\", \"changeAmount\": 0 }, { \"name\": \"White\", \"changeAmount\": 5 } ]"),
  (12, (SELECT id FROM product WHERE name = 'Microwave'), 'Size', '20L', "[ { \"name\": \"20L\", \"changeAmount\": 0 }, { \"name\": \"30L\", \"changeAmount\": 10 } ]"),
  (13, (SELECT id FROM product WHERE name = 'Refrigerator'), 'Energy Rating', 'A+', "[ { \"name\": \"A+\", \"changeAmount\": 0 }, { \"name\": \"A++\", \"changeAmount\": 50 } ]"),
  (14, (SELECT id FROM product WHERE name = 'T-shirt'), 'Size', 'M', "[ { \"name\": \"S\", \"changeAmount\": 0 }, { \"name\": \"M\", \"changeAmount\": 0 }, { \"name\": \"L\", \"changeAmount\": 0 } ]"),
  (15, (SELECT id FROM product WHERE name = 'Jeans'), 'Fit', 'Regular', "[ { \"name\": \"Regular\", \"changeAmount\": 0 }, { \"name\": \"Slim\", \"changeAmount\": 5 } ]"),
  (16, (SELECT id FROM product WHERE name = 'Jacket'), 'Material', 'Cotton', "[ { \"name\": \"Cotton\", \"changeAmount\": 0 }, { \"name\": \"Polyester\", \"changeAmount\": 10 } ]");

-- template product data
INSERT INTO template_product (id, name, price, description, tags, rating, category_id, created_at, updated_at) VALUES
  (UUID_TO_BIN(UUID()), 'Template Smartphone', 0.00, 'Template for smartphone', '', 0.00, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Template Laptop', 0.00, 'Template for laptop', '', 0.00, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Template Tablet', 0.00, 'Template for tablet', '', 0.00, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Template Smartwatch', 0.00, 'Template for smartwatch', '', 0.00, (SELECT id FROM category WHERE code = 'CAT001'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Template T-shirt', 0.00, 'Template for T-shirt', '', 0.00, (SELECT id FROM category WHERE code = 'CAT003'), NOW(), NOW()),
  (UUID_TO_BIN(UUID()), 'Template Jacket', 0.00, 'Template for jacket', '', 0.00, (SELECT id FROM category WHERE code = 'CAT003'), NOW(), NOW());

-- template attribute data
INSERT INTO template_attribute (id, name, default_value, options) VALUES
  (UUID_TO_BIN(UUID()), 'Color', 'Red', "[ { \"name\": \"Red\" }, { \"name\": \"Blue\" } ]"),
  (UUID_TO_BIN(UUID()), 'Size', 'M', "[ { \"name\": \"S\" }, { \"name\": \"M\" }, { \"name\": \"L\" } ]"),
  (UUID_TO_BIN(UUID()), 'Material', 'Cotton', "[ { \"name\": \"Cotton\" }, { \"name\": \"Polyester\" } ]"),
  (UUID_TO_BIN(UUID()), 'Fit', 'Regular', "[ { \"name\": \"Regular\" }, { \"name\": \"Slim\" } ]");

-- template product template attribute data
INSERT INTO template_product_template_attribute (template_attribute_id, template_product_id) VALUES
  ((SELECT id FROM template_attribute WHERE name = 'Color'), (SELECT id FROM template_product WHERE name = 'Template Smartphone')),
  ((SELECT id FROM template_attribute WHERE name = 'Size'), (SELECT id FROM template_product WHERE name = 'Template Laptop')),
  ((SELECT id FROM template_attribute WHERE name = 'Color'), (SELECT id FROM template_product WHERE name = 'Template Smartwatch')),
  ((SELECT id FROM template_attribute WHERE name = 'Size'), (SELECT id FROM template_product WHERE name = 'Template Tablet')),
  ((SELECT id FROM template_attribute WHERE name = 'Material'), (SELECT id FROM template_product WHERE name = 'Template T-shirt')),
  ((SELECT id FROM template_attribute WHERE name = 'Fit'), (SELECT id FROM template_product WHERE name = 'Template Jacket'));
