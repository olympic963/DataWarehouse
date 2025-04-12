Lưu ý: 
- Với file CreateDW.sql, thay bằng đường dẫn chính xác được lưu của file DimRepresentativeOffice.csv
- Sau khi tạo các bảng, chạy script CreateIndexDW.sql để tạo index cho các bảng. Nếu không tạo, mã nguồn sinh dữ liệu có thể bị chậm đáng kể. 
  Thực nghiệm cho thấy thời gian chèn từ 250k bản ghi trở đi đã tốn hơn 1,5h trong khi tạo index chỉ tốn khoảng 5p để chèn vào 1 triệu bản ghi. 
- Tại mã nguồn sinh dữ liệu, cập nhật mật khẩu đăng nhập vào SQL Server theo máy của người dùng trong file application.properties
- Note: số giao dịch tối đa có thể sinh = số item * số user * số lượng time_id
- Trong Sales.cube tại tab Dimension Usage, cần cấu hình Referenced Relationship giữa DimRepresentativeOffice và DimCustomer để SSAS hiểu được
  cách quan hệ của bảng thứ cấp này với FactSale, nếu không thì khi sử dụng Filter Expression hoặc thực hiện thống kê trên hierarchy với SSAS,
  thông số các độ đo theo từng state hoặc city sẽ không được tính đúng cách (trước khi chưa có cấu hình này thì dữ liệu các độ đo của từng state
  hoặc city giống hệt nhau và bằng tổng độ đo tất cả các bản ghi trong FactSale cộng lại) 
