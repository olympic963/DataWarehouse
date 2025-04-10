--CREATE DATABASE DW;
--GO
USE DW;
GO
DECLARE @sql NVARCHAR(MAX) = '';
SELECT @sql += 'ALTER TABLE ' + QUOTENAME(TABLE_SCHEMA) + '.' + QUOTENAME(TABLE_NAME) +
               ' DROP CONSTRAINT ' + QUOTENAME(CONSTRAINT_NAME) + '; '
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE CONSTRAINT_TYPE = 'FOREIGN KEY';

EXEC sp_executesql @sql;
DROP TABLE IF EXISTS DimTime;
DROP TABLE IF EXISTS DimItem;
DROP TABLE IF EXISTS DimRepresentativeOffice;
DROP TABLE IF EXISTS DimCustomer;
DROP TABLE IF EXISTS FactSale;

CREATE TABLE DimTime (
    time_id INT PRIMARY KEY,       -- yyyymmdd
    year INT NOT NULL,
    quarter TINYINT NOT NULL,
    month TINYINT NOT NULL,
    day TINYINT NOT NULL
);
CREATE TABLE DimItem (
    item_id INT PRIMARY KEY,
    description VARCHAR(255) NULL,
	price DECIMAL(15,3)
);

CREATE TABLE DimRepresentativeOffice (
    city_id INT PRIMARY KEY,
    city_name VARCHAR(50) NULL,
    state VARCHAR(50) NULL,
);
CREATE TABLE DimCustomer (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100) NULL,
    city_id INT NULL,
	customer_type INT NULL,
    FOREIGN KEY (city_id) REFERENCES DimRepresentativeOffice(city_id)
);
CREATE TABLE FactSale (
	time_id INT, 
	item_id INT,
	customer_id INT, 
	units_sold INT, 
	revenue DECIMAL(15,3),
	avg_sales DECIMAL(15,5),
	FOREIGN KEY (time_id) REFERENCES DimTime(time_id),
	FOREIGN KEY (item_id) REFERENCES DimItem(item_id),
	FOREIGN KEY (customer_id) REFERENCES DimCustomer(customer_id)	 
);