Lưu ý: 
- Với file CreateDW.sql, thay bằng đường dẫn chính xác được lưu của file DimRepresentativeOffice.csv
- Sau khi tạo các bảng, chạy script CreateIndexDW.sql để tạo index cho các bảng. Nếu không tạo, mã nguồn sinh dữ liệu có thể bị chậm đáng kể. 
  Thực nghiệm cho thấy thời gian chèn từ 250k bản ghi trở đi đã tốn hơn 1,5h trong khi tạo index chỉ tốn khoảng 5p để chèn vào 1 triệu bản ghi. 
- Tại mã nguồn sinh dữ liệu, cập nhật mật khẩu đăng nhập vào SQL Server theo máy của người dùng trong file application.properties
- Note: số giao dịch tối đa có thể sinh = số item * số user * số lượng time_id
- Khi thực hiện deploy SalesCube trong Visual Studio, cần tạo một instance khác của SQL Server khác trên máy và lưu ý tại các bước sau:
  1. Yêu cầu có Analysis Service, tích bỏ chọn Polybase Query Service for External Data nếu đã có instance khác (mặc định có sẵn instance MSSQLSERVER)
     ![image](https://github.com/user-attachments/assets/e40ee310-ec8d-48a0-a5bc-edd149c7e4ac)
  2. Chọn Server mode là Multimensional Mode thì SSAS mới có thể sinh cube.
     ![image](https://github.com/user-attachments/assets/a3f80283-d183-42e3-b667-503f0cd985eb)
  3. Cấu hình để cube được sinh ra trên instance vừa tạo: chuột phải vào tên project trong Solution Explorer, chọn Properties.
     Tại tab Deployment, cấu hình Server theo instance vừa tạo, Databse đặt tên tùy ý, Visual Studio sẽ tự sinh ra nếu DB chưa tồn tại  
     ![image](https://github.com/user-attachments/assets/bbbb5724-0e5d-417f-bdab-06ee7d000702)    
- Trong Sales.cube tại tab Dimension Usage, cần cấu hình Referenced Relationship giữa DimRepresentativeOffice và DimCustomer để SSAS hiểu được
  cách quan hệ của bảng thứ cấp này với FactSale, nếu không thì khi sử dụng Filter Expression hoặc thực hiện thống kê trên hierarchy với SSAS,
  thông số các độ đo theo từng state hoặc city sẽ không được tính đúng cách (trước khi chưa có cấu hình này thì dữ liệu các độ đo của từng state
  hoặc city giống hệt nhau và bằng tổng độ đo tất cả các bản ghi trong FactSale cộng lại)
  
