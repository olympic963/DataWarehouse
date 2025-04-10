Lưu ý: 
- Với file CreateDW.sql, thay bằng đường dẫn chính xác được lưu của file DimRepresentativeOffice.csv
- Sau khi tạo các bảng, chạy script CreateIndexDW.sql để tạo index cho các bảng. Nếu không tạo, mã nguồn sinh dữ liệu có thể bị chậm đáng kể. 
  Thực nghiệm cho thấy thời gian chèn từ 250k bản ghi trở đi đã tốn hơn 1,5h trong khi tạo index chỉ tốn khoảng 5p để chèn vào 1 triệu bản ghi. 
- Tại mã nguồn sinh dữ liệu, cập nhật mật khẩu đăng nhập vào SQL Server theo máy của người dùng trong file application.properties
