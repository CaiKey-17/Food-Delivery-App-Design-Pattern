## Video demo của project cũ
[Xem tại đây](https://drive.google.com/drive/u/0/folders/1pX57T5Rwly1kJpRZ1vkrsjL9Gw-AGIYy?fbclid=IwY2xjawG4BTlleHRuA2FlbQIxMAABHcuZh0cfnBBrv2MrHfhuz6-T4c75OnKCN9aedcpfyPc-Qf6th_md1S9Ppw_aem_0BiuiPxAy_Ce_kXBjkML6g)

Để có những trải nghiệm mới, chúng ta nên khởi chạy Project mới này !

## Setup project
### 1. **Chuẩn bị môi trường làm việc**
1. **Fork repository chính:**
   - Truy cập repository trên GitHub và nhấn **"Fork"** để tạo một bản sao về tài khoản cá nhân.

2. **Clone repository về máy:**
   - Sử dụng lệnh sau để sao chép repository fork về máy tính:
     ```bash
     git clone https://github.com/your-username/repository-name.git
     cd repository-name
     ```

3. **Tạo nhánh làm việc mới:**
   - Tạo một nhánh riêng để phát triển tính năng hoặc sửa lỗi:
     ```bash
     git checkout -b feature/your-feature-name
     ```

---

### 2. **Thiết lập cơ sở dữ liệu và API**

#### **Cấu hình cơ sở dữ liệu:**
1. **Tạo cơ sở dữ liệu:**
   - Mở công cụ quản lý MySQL (MySQL Workbench, phpMyAdmin, hoặc command line).
   - Tạo một cơ sở dữ liệu với tên **`Project_Android`**:
     ```sql
     CREATE DATABASE project_android với utf8mb4_general_ci;
     ```

2. **Import dữ liệu mẫu:**
   - Tìm file **`Project_Android.sql`** trong thư mục dự án.
   - Import dữ liệu vào cơ sở dữ liệu **`Project_Android`** bằng một trong các cách sau:
     - **MySQL Workbench:** Chọn cơ sở dữ liệu và sử dụng tính năng **"Run SQL Script"**.

#### **Cấu hình kết nối API:**
1. **Cập nhật đường dẫn cơ sở dữ liệu:**
   - Tìm đến file **`src/main/java/com/example/Api/global/Connection.java`** trong thư mục `api`.
   - Thay đổi thông tin kết nối cơ sở dữ liệu:
     ```java
      public final  static  String type_connection = "mysql";
      public final  static  String database =  "jdbc:mysql://localhost:3306/project_android";
      public final  static  String username = "...";
      public final  static  String password = "...";
     ```
     - **`type_connection`** : Thông tin loại CSDL bạn sử dụng.

     - **`username`** và **`password`**: Thông tin tài khoản MySQL của bạn.

2. **Kiểm tra API chạy đúng địa chỉ:**
   - Đảm bảo API được cấu hình để hoạt động tại địa chỉ đúng. Nếu cần chạy tại localhost:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/project_android";
     ```

3. **Cấu hình ứng dụng kết nối API:**
   - Tìm file **`network_security_config.xml`** trong thư mục `app`.
   - Cập nhật IP hoặc domain của server API:
     ```xml
     <domain includeSubdomains="true">"......(Nhập IPv4 của bạn tại đây)....."</domain>
     ```

4. **Cập nhật API_KEY:**
   - Mở file **`AndroidManifest.xml`** và thay giá trị **API_KEY** bằng khóa API thực tế của bạn:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="your-api-key" />
     ```

5. **Mở source/src/api: để khởi chạy server**
   ```xml
    mvn spring-boot:run
     ```



6. **Mở source/src/app: để khởi chạy ứng dụng**
   - Sử dụng một số tài khoản có sẵn sau (có thể dùng chức năng "Đăng ký" để tạo tài khoản mới tùy ý):

      - **Khách hàng**: 123 (Tài khoản), 123 (Mật khẩu)

      - **Admin**: 1234 (Tài khoản), 123 (Mật khẩu)

      - Còn tài khoản **nhà hàng**: dựa vào đơn hàng mua của khách hàng để sử dụng tương ứng.
   
      - **Tài xế**: tự tạo + nạp tiền và đợi Admin phê duyệt mới có thể hoạt động.


7. **Để test các chức năng thu thập & sử dụng khuyến mãi**
   - Cập nhật thời gian hạn sử dụng lại trong sql
   - Có thể thêm tùy ý khi đăng nhập vào **nhà hàng**, **admin**




---

### 3. **Thực hiện thay đổi**
1. **Cập nhật và chỉnh sửa mã nguồn:**
   - Thực hiện các thay đổi cần thiết trong mã nguồn.
   - Đảm bảo tuân thủ các quy tắc mã hóa và tài liệu của dự án.

2. **Kiểm tra mã:**
   - Chạy các bài kiểm tra tự động (nếu có) để đảm bảo không gây lỗi:
     ```bash
     ./run-tests.sh
     ```
   - Kiểm tra mã nguồn bằng công cụ lint để đảm bảo tiêu chuẩn mã hóa:
     ```bash
     ./lint-check.sh
     ```

---

### 4. **Gửi thay đổi của bạn**
1. **Commit thay đổi:**
   - Lưu lại các thay đổi của bạn với thông điệp commit rõ ràng:
     ```bash
     git add .
     git commit -m "Mô tả ngắn gọn thay đổi của bạn"
     ```

2. **Push nhánh của bạn:**
   - Đẩy nhánh làm việc lên repository fork của bạn:
     ```bash
     git push origin feature/your-feature-name
     ```

3. **Tạo Pull Request (PR):**
   - Truy cập repository gốc trên GitHub và nhấn **"Compare & Pull Request"**.
   - Điền thông tin chi tiết về thay đổi và lý do thực hiện.
   - Gửi Pull Request để nhóm phát triển xem xét.

---

### 5. **Chờ phê duyệt**
- Nhóm phát triển sẽ xem xét Pull Request của bạn.
- Nếu cần chỉnh sửa, bạn sẽ nhận được phản hồi và cần thực hiện thay đổi trên nhánh của mình.
- Khi Pull Request được phê duyệt, thay đổi của bạn sẽ được hợp nhất vào dự án.

---

### 6. **Lưu ý chung**
1. **Cấu hình IP và API_KEY chính xác:**
   - Luôn đảm bảo đường dẫn API và IP kết nối tới cơ sở dữ liệu là đúng.
   - Đừng để thông tin nhạy cảm (như mật khẩu hoặc API_KEY) trong mã nguồn công khai. Sử dụng biến môi trường hoặc các công cụ quản lý bảo mật.

2. **Tuân thủ quy tắc mã hóa:**
   - Đọc kỹ tài liệu hướng dẫn để đảm bảo thay đổi phù hợp với cấu trúc và tiêu chuẩn của dự án.

3. **Kiểm tra kỹ lưỡng:**
   - Kiểm tra kỹ trước khi gửi Pull Request, tránh những lỗi không cần thiết hoặc sai sót lớn.

---

Chúng tôi mong chờ những đóng góp từ bạn để làm cho dự án ngày càng hoàn thiện hơn!
