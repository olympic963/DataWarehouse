--CREATE DATABASE IDB;
--GO
USE IDB;
GO
DECLARE @sql NVARCHAR(MAX) = '';
SELECT @sql += 'ALTER TABLE ' + QUOTENAME(TABLE_SCHEMA) + '.' + QUOTENAME(TABLE_NAME) +
               ' DROP CONSTRAINT ' + QUOTENAME(CONSTRAINT_NAME) + '; '
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE CONSTRAINT_TYPE = 'FOREIGN KEY';

EXEC sp_executesql @sql;
DROP TABLE IF EXISTS RepresentativeOffice;
DROP TABLE IF EXISTS Store;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS TouristCustomer;
DROP TABLE IF EXISTS PostalCustomer;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS StoredItem;
DROP TABLE IF EXISTS OrderItem;
DROP TABLE IF EXISTS [Order];
CREATE TABLE RepresentativeOffice (
    city_id INT PRIMARY KEY,
    city_name VARCHAR(50) NULL,
    office_address VARCHAR(100) NULL,
    state VARCHAR(50) NULL,
    establishment_date DATE NULL
);

CREATE TABLE Store (
    store_id INT PRIMARY KEY,
    city_id INT NULL,
    phone_number VARCHAR(20) NULL,
    opening_date DATE NULL,
    FOREIGN KEY (city_id) REFERENCES RepresentativeOffice(city_id)
);

CREATE TABLE Item (
    item_id INT PRIMARY KEY,
    description VARCHAR(255) NULL,
    weight DECIMAL(10,2) NULL,
    price DECIMAL(15,3) NULL,
    sale_start_date DATETIME NULL
);

CREATE TABLE Customer (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100) NULL,
    city_id INT NULL,
    first_order_date DATE NULL,
    FOREIGN KEY (city_id) REFERENCES RepresentativeOffice(city_id)
);

CREATE TABLE TouristCustomer (
    customer_id INT PRIMARY KEY,
    tour_guide VARCHAR(100) NULL,
    first_travel_date DATE NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE PostalCustomer (
    customer_id INT PRIMARY KEY,
    postal_address VARCHAR(100) NULL,
    registration_date DATE NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE [Order] (
    order_id INT PRIMARY KEY,
    order_date DATE NULL,
    customer_id INT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE StoredItem (
    store_id INT,
    item_id INT,
    quantity_in_stock INT NULL,
    update_time DATETIME NULL,
    PRIMARY KEY (store_id, item_id),
    FOREIGN KEY (store_id) REFERENCES Store(store_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id)
);

CREATE TABLE OrderItem (
    order_id INT,
    item_id INT,
    order_quantity INT NULL,
    order_price DECIMAL(15,3) NULL,
    delivery_date DATE NULL,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES [Order](order_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id)
);
