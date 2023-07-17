DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS product_warehouse;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS warehouses;


CREATE TABLE discounts
(
    discount_id    serial PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    percent  SMALLINT UNIQUE
);

CREATE TABLE users
(
    user_id       serial PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    password      VARCHAR(50)         NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    user_discount INT                 NOT NULL,
    CONSTRAINT fk_user_discount FOREIGN KEY (user_discount) REFERENCES discounts (discount_id) ON DELETE CASCADE
);


CREATE TABLE products
(
    product_id  serial PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE warehouses
(
    warehouse_id serial PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    location     point NOT NULL
);

CREATE TABLE product_warehouse
(
    product_id   INT,
    warehouse_id INT,
    PRIMARY KEY (product_id, warehouse_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id) ON DELETE CASCADE
)
