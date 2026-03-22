# CHƯƠNG I: TỔNG QUAN

## 1.1. Giới thiệu tổng quát

Trong bối cảnh chuyển đổi số diễn ra mạnh mẽ trong nhiều lĩnh vực đời sống, các ứng dụng công nghệ thông tin ngày càng được áp dụng rộng rãi nhằm hỗ trợ công tác quản lý cá nhân và chia sẻ thông tin. Một trong những nhu cầu thiết thực trong cuộc sống hiện đại là việc quản lý kiến thức về ẩm thực, lưu trữ công thức nấu ăn và lập kế hoạch bữa ăn một cách hiệu quả, khoa học và thuận tiện.

Việc ghi chép công thức nấu ăn truyền thống bằng sổ tay hoặc ghi nhớ thủ công dễ gây thất lạc thông tin, khó tìm kiếm khi cần và không có khả năng chia sẻ với cộng đồng. Đặc biệt, việc lập kế hoạch bữa ăn hàng tuần và quản lý dinh dưỡng cũng là một thách thức đối với nhiều người.

Ứng dụng quản lý công thức nấu ăn trên nền tảng web ra đời nhằm giúp người dùng lưu trữ công thức, tổ chức kế hoạch bữa ăn và chia sẻ kiến thức ẩm thực một cách trực quan và linh hoạt. Xuất phát từ nhu cầu thực tế đó, em đã lựa chọn thực hiện đề tài **"Xây dựng hệ thống Quản lý và Chia sẻ Công thức Nấu ăn - Recipe Discovery"** trong khuôn khổ học phần Đồ án Công nghệ Phần mềm 1, nhằm vận dụng kiến thức đã học để xây dựng một sản phẩm phần mềm có tính ứng dụng cao.

## 1.2. Mục đích đề tài

Mục đích của đề tài là xây dựng một hệ thống web application giúp người dùng dễ dàng quản lý công thức nấu ăn cá nhân và chia sẻ với cộng đồng. Cụ thể:

- Xây dựng hệ thống cho phép người dùng tạo, chỉnh sửa, xóa và quản lý công thức nấu ăn cá nhân.
- Hỗ trợ lưu trữ thông tin chi tiết về món ăn: tên món, nguyên liệu, hướng dẫn nấu, ảnh minh họa, thông tin dinh dưỡng.
- Xây dựng tính năng lập kế hoạch bữa ăn (meal planning) theo tuần với 4 khung giờ chính: sáng, trưa, phụ, tối.
- Tích hợp hệ thống thông báo email tự động để nhắc nhở người dùng về bữa ăn đã lên lịch.
- Xây dựng nền tảng cộng đồng cho phép người dùng chia sẻ công thức và khám phá món ăn mới.
- Vận dụng quy trình phát triển phần mềm và các kiến thức đã học trong lĩnh vực công nghệ phần mềm, đặc biệt là Spring Boot framework.
- Thông qua đề tài, sinh viên được rèn luyện kỹ năng phân tích yêu cầu, thiết kế hệ thống, phát triển web application và làm việc với database.

## 1.3. Đối tượng và phạm vi nghiên cứu

**Đối tượng nghiên cứu** của đề tài là các vấn đề liên quan đến việc xây dựng hệ thống quản lý công thức nấu ăn trên nền tảng web, bao gồm người dùng (users), công thức nấu ăn (recipes), kế hoạch bữa ăn (meal plans) và các chức năng quản lý liên quan.

**Phạm vi nghiên cứu** của đề tài tập trung vào:

*Về chức năng:*
- Phát triển hệ thống quản lý công thức nấu ăn cá nhân với đầy đủ CRUD operations.
- Xây dựng hệ thống meal planning cho phép người dùng lập kế hoạch bữa ăn theo tuần.
- Triển khai scheduled tasks để gửi email thông báo tự động 4 lần/ngày.
- Xây dựng nền tảng chia sẻ cộng đồng (community platform) với khả năng public/private recipes.
- Phát triển tính năng phân quyền (USER/ADMIN) và admin dashboard.
- Xây dựng hệ thống categories tùy chỉnh cho phép người dùng tổ chức công thức.

*Về công nghệ:*
- Backend: Spring Boot 3.2.2, Spring MVC, Spring Data JPA, Spring Mail, Spring Scheduler
- Frontend: Thymeleaf template engine, Bootstrap 5.3.3, JavaScript
- Database: MySQL 8.x với charset UTF8MB4
- Email Service: JavaMailSender với Gmail SMTP
- Architecture: MVC pattern với Service layer

*Giới hạn phạm vi:*
- Đề tài chưa đi sâu vào các tính năng nâng cao như tích hợp AI để gợi ý công thức, phân tích dinh dưỡng tự động, hoặc xây dựng mobile application.
- Chưa triển khai payment gateway cho premium features.
- Chưa hỗ trợ đa ngôn ngữ (internationalization).
- Password encryption sử dụng cơ bản (chưa implement BCrypt - sẽ cải tiến).

## 1.4. Phương pháp nghiên cứu

Trong quá trình thực hiện đề tài, em sử dụng các phương pháp nghiên cứu sau:

**Nghiên cứu tài liệu:** Tìm hiểu các giáo trình, tài liệu liên quan đến phát triển web application với Spring Boot framework, nghiên cứu Spring ecosystem (Spring MVC, Spring Data JPA, Spring Mail, Spring Scheduler), tìm hiểu về Thymeleaf template engine và MySQL database.

**Phân tích yêu cầu:** Khảo sát nhu cầu thực tế về quản lý công thức nấu ăn và meal planning, phân tích các ứng dụng tương tự (Yummly, Allrecipes), xác định các chức năng cần thiết cho hệ thống. Sử dụng phương pháp Use Case analysis để xác định 8 nhóm chức năng chính với 20+ use cases.

**Thiết kế hệ thống:** Áp dụng mô hình MVC (Model-View-Controller) với Service layer. Xây dựng kiến trúc tầng (Presentation, Controller, Service, Repository, Database). Thiết kế database schema với 6 bảng chính và các mối quan hệ. Thiết kế giao diện người dùng responsive với Bootstrap framework.

**Thực nghiệm và kiểm thử:** Triển khai ứng dụng theo quy trình Agile với các sprint ngắn. Thực hiện unit testing cho service layer, integration testing cho controllers. Kiểm tra và đánh giá mức độ đáp ứng yêu cầu của hệ thống thông qua manual testing và user acceptance testing.

## 1.5. Nội dung thực hiện của đề tài

### 1.5.1. Phân tích yêu cầu

Thu thập và phân tích các yêu cầu chức năng và phi chức năng của hệ thống Recipe Discovery. Xác định 8 nhóm chức năng chính:

- **F1:** Quản lý người dùng & Xác thực (đăng ký, đăng nhập, phân quyền USER/ADMIN)
- **F2:** Quản lý công thức cá nhân (CRUD operations, upload ảnh, phân loại)
- **F3:** Cộng đồng chia sẻ (public/private recipes, favorites, search)
- **F4:** Kế hoạch bữa ăn (meal planning cho 4 bữa x 7 ngày/tuần)
- **F5:** Thông báo email tự động (scheduled tasks gửi 4 lần/ngày)
- **F6:** Quản lý categories (tạo, sửa, xóa danh mục tùy chỉnh)
- **F7:** Admin dashboard (quản lý users, kiểm duyệt nội dung)
- **F8:** Tính toán dinh dưỡng (nutrition calculator cơ bản)

Xác định yêu cầu phi chức năng về Performance (response time < 2s), Security (session-based authentication), Usability (responsive design), Maintainability (clean code, MVC), và Reliability (error handling, logging).

### 1.5.2. Thiết kế hệ thống

Thiết kế tổng thể hệ thống, bao gồm:

**Thiết kế kiến trúc hệ thống:**
- Áp dụng mô hình MVC với 4 tầng: Presentation (Thymeleaf), Controller (Spring MVC), Service (Business Logic), Repository (Spring Data JPA)
- Tầng Presentation sử dụng Thymeleaf templates với Bootstrap 5.3.3
- Tầng Controller xử lý HTTP requests với 10 controller classes
- Tầng Service chứa business logic với 6 service classes
- Tầng Repository sử dụng Spring Data JPA với 5 repository interfaces

**Thiết kế cơ sở dữ liệu:**
- Thiết kế database schema với 6 bảng chính: users, recipes, meal_plans, meal_plan_items, user_categories, favorites
- Xác định relationships: users 1:N recipes, recipes M:N favorites, meal_plans 1:N meal_plan_items
- Vẽ ERD diagram thể hiện toàn bộ mối quan hệ giữa các entities
- Normalize database đến 3NF để tránh redundancy

**Thiết kế giao diện người dùng:**
- Thiết kế 17 Thymeleaf templates cho các chức năng khác nhau
- Sử dụng Bootstrap grid system để đảm bảo responsive trên mọi thiết bị
- Thiết kế email template với HTML/CSS inline để đảm bảo tương thích email clients
- Áp dụng color scheme nhất quán với main color #22c55e (green)

### 1.5.3. Phát triển ứng dụng

Tiến hành cài đặt hệ thống theo thiết kế đã đề ra:

**Backend Development (Spring Boot):**
- Setup Spring Boot project với Maven, cấu hình dependencies (Web, JPA, Mail, Thymeleaf)
- Implement 5 entities với JPA annotations (@Entity, @Table, @Column, relationships)
- Implement 5 repositories extends JpaRepository với custom query methods
- Implement 6 services chứa business logic, transaction management (@Transactional)
- Implement 10 controllers xử lý HTTP requests, validation, error handling
- Implement scheduled tasks với @Scheduled annotations cho email notifications
- Configure JavaMailSender với Gmail SMTP settings

**Frontend Development (Thymeleaf + Bootstrap):**
- Tạo 17 Thymeleaf templates với Thymeleaf syntax (th:text, th:each, th:if...)
- Implement responsive layout với Bootstrap 5.3.3 components
- Sử dụng Bootstrap Icons 1.11.3 cho icons
- JavaScript cho dynamic interactions (modals, form validation...)

**Database Integration:**
- Cấu hình DataSource trong application.yml
- Setup Hibernate với MySQL dialect
- Configure JPA properties (ddl-auto, show-sql, naming strategy)

### 1.5.4. Tối ưu và cải tiến

Kiểm thử hệ thống, phát hiện và khắc phục lỗi:

- Phát hiện và sửa LazyInitializationException bằng cách thêm @Transactional và eager loading
- Optimize database queries với pagination (12 items/page)
- Implement proper error handling với try-catch blocks và logging
- Code refactoring để cải thiện code quality và maintainability
- UI/UX improvements dựa trên testing feedback

### 1.5.5. Hướng dẫn sử dụng và triển khai

Xây dựng tài liệu hướng dẫn:
- QUICK-START.md: Hướng dẫn chạy app từ IntelliJ IDEA và test email notifications
- SETUP-GUIDE.md: Hướng dẫn setup môi trường, cấu hình Gmail App Password, build và test
- SYSTEM-ANALYSIS.md: Báo cáo phân tích hệ thống chi tiết với workflows, use cases
- README.md: Tổng quan về project và các tài liệu liên quan

## 1.6. Môi trường ứng dụng

Hệ thống Recipe Discovery được phát triển và triển khai trong các môi trường sau:

**Môi trường phát triển:**
- **IDE:** IntelliJ IDEA 2024
- **JDK:** Java 17 (LTS)
- **Build Tool:** Apache Maven 3.9.x
- **Database:** MySQL 8.0.33
- **OS:** Windows 10/11
- **Version Control:** Git + GitHub

**Công nghệ Backend:**
- **Framework:** Spring Boot 3.2.2
- **Spring Modules:** Spring MVC, Spring Data JPA, Spring Mail, Spring Scheduler
- **ORM:** Hibernate 6.4.1
- **Database Driver:** MySQL Connector
- **Utilities:** Lombok (code generation)

**Công nghệ Frontend:**
- **Template Engine:** Thymeleaf 3.1.2
- **CSS Framework:** Bootstrap 5.3.3
- **Icons:** Bootstrap Icons 1.11.3
- **JavaScript:** Vanilla JS (ES6+)

**Database:**
- **DBMS:** MySQL 8.x
- **Character Set:** UTF8MB4
- **Collation:** utf8mb4_unicode_ci
- **Storage Engine:** InnoDB
- **Tables:** 6 main tables

**Email Service:**
- **Protocol:** SMTP
- **Provider:** Gmail
- **Library:** JavaMailSender (Spring Mail)
- **Authentication:** App Password

**Yêu cầu hệ thống tối thiểu:**
- Java 17 hoặc cao hơn
- MySQL 8.0 hoặc cao hơn
- RAM: 4GB
- Disk space: 500MB
- Port: 8080 (application), 3306 (MySQL)

## 1.7. Kết quả thực hiện

Kết quả đạt được của đề tài bao gồm:

**Về sản phẩm:**
- Xây dựng thành công hệ thống Recipe Discovery đáp ứng 100% các yêu cầu chức năng đã đề ra.
- Hệ thống hoạt động ổn định với average response time < 1 giây.
- Giao diện responsive, thân thiện và dễ sử dụng trên mọi thiết bị.
- Email notification system hoạt động chính xác với 4 scheduled tasks mỗi ngày.
- Admin dashboard đầy đủ chức năng quản lý users và content moderation.

**Về kỹ thuật:**
- Codebase: ~10,000 lines of code (Java + HTML/CSS/JS)
- Database: 6 tables với relationships rõ ràng, normalized đến 3NF
- API Endpoints: 30+ controller methods
- Templates: 17 Thymeleaf templates
- Test coverage: Manual testing cho tất cả chức năng chính

**Về kiến thức:**
- Nắm vững Spring Boot ecosystem và best practices
- Thành thạo mô hình MVC và layered architecture
- Hiểu rõ ORM (Hibernate), JPA annotations và database design
- Biết cách implement scheduled tasks và email integration
- Có kinh nghiệm về session management và role-based authorization

**Về kỹ năng:**
- Tích lũy được kinh nghiệm thực tế trong việc phát triển web application theo quy trình chuẩn
- Rèn luyện kỹ năng phân tích, thiết kế và implement hệ thống phức tạp
- Phát triển kỹ năng debug, troubleshooting và problem-solving
- Học được cách đọc documentation, tìm kiếm thông tin và self-learning

**Hạn chế cần cải thiện:**
- Password chưa được encrypt bằng BCrypt (plaintext - security issue)
- Chưa có comprehensive unit test suite
- Chưa optimize cho very large datasets (caching, indexing...)
- Chưa deploy lên production environment (cloud platform)

Đây là nền tảng để tiếp tục mở rộng và nâng cấp hệ thống trong tương lai với các tính năng như: BCrypt password encryption, comprehensive testing, performance optimization với Redis cache, real-time notifications với WebSocket, mobile app development, và deployment lên AWS/Azure.

---

**Tổng kết:** Chương I đã trình bày tổng quan về đề tài "Recipe Discovery", làm rõ mục đích, phạm vi, phương pháp nghiên cứu và kết quả đạt được. Các chương tiếp theo sẽ đi sâu vào cơ sở lý thuyết, phân tích thiết kế hệ thống, giao diện và kết luận.
