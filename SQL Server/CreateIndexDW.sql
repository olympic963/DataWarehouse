USE DW;
GO

-- 1. Index trên DimCustomer
CREATE NONCLUSTERED INDEX idx_DimCustomer_customer_id
ON dbo.DimCustomer(customer_id);
GO

-- 2. Index trên DimTime
CREATE NONCLUSTERED INDEX idx_DimTime_time_id
ON dbo.DimTime(time_id);
GO

-- 3. Index trên DimItem
CREATE NONCLUSTERED INDEX idx_DimItem_item_id
ON dbo.DimItem(item_id);
GO

-- 4. Index trên DimRepresentativeOffice
CREATE NONCLUSTERED INDEX idx_DimRepOffice_city_id
ON dbo.DimRepresentativeOffice(city_id);
GO

-- 5. Index tổng hợp trên FactSale (dùng cho truy vấn WHERE 3 cột)
CREATE NONCLUSTERED INDEX idx_FactSale_customer_item_time
ON dbo.FactSale(customer_id, item_id, time_id);
GO
