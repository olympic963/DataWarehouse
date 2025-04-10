INSERT INTO DW.dbo.DimTime (time_id, year, quarter, month, day)
SELECT 
    CAST(CONVERT(VARCHAR(8), order_date, 112) AS INT) AS time_id,
    YEAR(order_date) AS year,
    DATEPART(QUARTER, order_date) AS quarter,
    MONTH(order_date) AS month,
    DAY(order_date) AS day
FROM IDB.dbo.[Order]
WHERE order_date IS NOT NULL
GROUP BY order_date;

INSERT INTO DW.dbo.DimItem (item_id, description, price)
SELECT 
    item_id,
    ISNULL(description, 'unknown_item_descripton_' + CAST(item_id AS VARCHAR(10))) AS description,
    ISNULL(price, 0) AS price
FROM IDB.dbo.Item;

INSERT INTO DW.dbo.DimRepresentativeOffice (city_id, city_name, state)
SELECT 
    city_id,
    ISNULL(city_name, 'unknown_city_name_' + CAST(city_id AS VARCHAR(10))) AS city_name,
    ISNULL(state, 'unknown_state_' + CAST(city_id AS VARCHAR(10))) AS state
FROM IDB.dbo.RepresentativeOffice;

INSERT INTO DW.dbo.DimCustomer (customer_id, customer_name, city_id, customer_type)
SELECT 
    c.customer_id,
    ISNULL(c.customer_name, 'unknown_customer_name_' + CAST(c.customer_id AS VARCHAR(10))) AS customer_name,
    c.city_id,      
    CASE 
        WHEN tc.customer_id IS NOT NULL AND pc.customer_id IS NOT NULL THEN 2
        WHEN tc.customer_id IS NOT NULL THEN 1
        WHEN pc.customer_id IS NOT NULL THEN 0
        ELSE NULL    
    END AS customer_type
FROM IDB.dbo.Customer c
LEFT JOIN IDB.dbo.TouristCustomer tc ON c.customer_id = tc.customer_id
LEFT JOIN IDB.dbo.PostalCustomer pc ON c.customer_id = pc.customer_id;

INSERT INTO DW.dbo.FactSale (time_id, item_id, customer_id, units_sold, revenue, avg_sales)
SELECT 
    CAST(CONVERT(VARCHAR(8), o.order_date, 112) AS INT) AS time_id,
    oi.item_id,
    o.customer_id,
    ISNULL(oi.order_quantity, 0) AS units_sold,
    ISNULL(oi.order_quantity * oi.order_price, 0) AS revenue,
    CASE 
        WHEN ISNULL(oi.order_quantity, 0) > 0 
             AND oi.order_price IS NOT NULL 
        THEN ISNULL((oi.order_quantity * oi.order_price) / oi.order_quantity, 0)
        ELSE 0 
    END AS avg_sales
FROM IDB.dbo.[Order] o
JOIN IDB.dbo.OrderItem oi ON o.order_id = oi.order_id
WHERE o.order_date IS NOT NULL; 







