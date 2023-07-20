DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS product_warehouse;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS warehouses;


CREATE TABLE discounts
(
    id      serial PRIMARY KEY,
    code    VARCHAR(50) UNIQUE NOT NULL,
    name    VARCHAR(100)       NOT NULL,
    percent SMALLINT UNIQUE    NOT NULL
);

CREATE TABLE users
(
    id            serial PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    password      VARCHAR(50)         NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    user_discount INT                 NOT NULL,
    CONSTRAINT fk_user_discount FOREIGN KEY (user_discount) REFERENCES discounts (id) ON DELETE CASCADE
);

CREATE TABLE employees
(
    id          serial PRIMARY KEY,
    salary      INT  NOT NULL,
    hiring_date date NOT NULL
);


CREATE TABLE customers
(
    id                serial PRIMARY KEY,
    registration_date date NOT NULL
);



CREATE TABLE products
(
    id          serial PRIMARY KEY,
    barcode     INT          NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR      NOT NULL
);

CREATE INDEX barcode_index ON products (barcode);

CREATE TABLE warehouses
(
    id   serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lat  float,
    long float
);

CREATE TABLE product_warehouse
(
    product_id   INT,
    warehouse_id INT,
    PRIMARY KEY (product_id, warehouse_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (id) ON DELETE CASCADE
)
