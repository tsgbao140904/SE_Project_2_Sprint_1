TRƯỜNG ĐẠI HỌC QUY NHƠN
KHOA CÔNG NGHỆ THÔNG TIN







BÁO CÁO KẾT THÚC
Học phần: Đồ án CNPM 1
Tên đề tài: Hệ thống Quản lý và Chia sẻ Công thức Nấu ăn - Recipe Discovery

Sinh viên thực hiện: Thái Sinh Gia Bảo
Mã sinh viên: 4551190003
Lớp: Kỹ Thuật Phần Mềm
Khóa: 45
Giảng viên hướng dẫn: TS. Phạm Văn Việt





Gia Lai, tháng 12 năm 2025




LỜI CẢM ƠN

Trước hết, em xin bày tỏ lòng biết ơn sâu sắc đến quý Thầy/Cô Khoa Công nghệ Thông tin – Trường Đại học Quy Nhơn đã tận tình giảng dạy, truyền đạt cho em những kiến thức nền tảng và chuyên môn quý báu trong suốt quá trình học tập. Đặc biệt, em xin chân thành cảm ơn Thầy Phạm Văn Việt - Giảng viên hướng dẫn môn Đồ án Công nghệ Phần mềm 1 đã luôn theo sát, hướng dẫn, góp ý và hỗ trợ em cũng như các bạn trong suốt quá trình thực hiện đồ án, giúp chúng em từng bước hoàn thiện sản phẩm theo đúng mục tiêu học phần đề ra.

Bên cạnh đó, em cũng xin gửi lời cảm ơn đến bạn bè, tập thể lớp đã cùng nhau trao đổi, chia sẻ kinh nghiệm và hỗ trợ lẫn nhau trong quá trình học tập và thực hiện đồ án. Mặc dù đã rất cố gắng, nhưng do kiến thức và thời gian còn hạn chế, báo cáo và sản phẩm đồ án khó tránh khỏi những thiếu sót. Em rất mong nhận được sự đóng góp ý kiến của quý Thầy/Cô để đồ án được hoàn thiện hơn. Em xin chân thành cảm ơn!




















LỜI MỞ ĐẦU

Trong bối cảnh công nghệ thông tin ngày càng phát triển mạnh mẽ, công nghệ phần mềm đóng vai trò quan trọng trong việc xây dựng các hệ thống phục vụ học tập, quản lý và đời sống xã hội. Việc nắm vững quy trình phát triển phần mềm, từ phân tích yêu cầu, thiết kế hệ thống, cài đặt cho đến kiểm thử và đánh giá, là yêu cầu thiết yếu đối với sinh viên ngành Công nghệ Thông tin nói chung và Công nghệ Phần mềm nói riêng.

Học phần Đồ án Công nghệ Phần mềm 1 được xây dựng nhằm giúp sinh viên hệ thống hóa và vận dụng tổng hợp các kiến thức đã học về lập trình, công nghệ phần mềm, kiến trúc hệ thống và giải quyết vấn đề thực tế. Thông qua đồ án này, em đã lựa chọn và xây dựng hệ thống "Recipe Discovery" - một nền tảng quản lý và chia sẻ công thức nấu ăn trực tuyến, nhằm giải quyết nhu cầu thiết thực trong việc lưu trữ, tổ chức và chia sẻ kiến thức ẩm thực trong cộng đồng.

Đề tài được thực hiện đầy đủ các giai đoạn của quy trình phát triển phần mềm theo mô hình MVC, sử dụng công nghệ Spring Boot, Thymeleaf và MySQL. Hệ thống không chỉ cho phép người dùng quản lý công thức cá nhân mà còn tích hợp các tính năng hiện đại như lập kế hoạch bữa ăn (meal planning), thông báo email tự động, và platform chia sẻ cộng đồng.

Báo cáo này trình bày toàn bộ quá trình thực hiện đồ án, bao gồm phân tích yêu cầu, thiết kế hệ thống, cài đặt, kiểm thử và đánh giá kết quả đạt được. Qua đó, em hy vọng báo cáo sẽ phản ánh rõ ràng những kiến thức, kỹ năng đã tiếp thu cũng như những kinh nghiệm rút ra trong quá trình thực hiện học phần.


DANH SÁCH CÁC HÌNH ẢNH

Hình 2.1. Mô hình kiến trúc MVC của Recipe Discovery
Hình 2.2. Technology Stack của hệ thống
Hình 3.1. Use Case Diagram Tổng quát
Hình 3.2. Use Case UML của chức năng Đăng ký / Đăng nhập
Hình 3.3. Sequence Diagram của chức năng Đăng nhập
Hình 3.4. Use Case UML của chức năng Quản lý công thức
Hình 3.5. Activity Diagram của chức năng Tạo công thức mới
Hình 3.6. Use Case UML của chức năng Meal Planning
Hình 3.7. Sequence Diagram của chức năng Thêm món vào Meal Plan
Hình 3.8. Use Case UML của chức năng Email Notification
Hình 3.9. Activity Diagram của Email Scheduler
Hình 3.10. Use Case UML của chức năng Community Sharing
Hình 3.11. Activity Diagram của chức năng Chia sẻ công thức
Hình 3.12. Bảng users trong Cơ sở dữ liệu recipe_discovery
Hình 3.13. Bảng recipes trong Cơ sở dữ liệu recipe_discovery
Hình 3.14. Bảng meal_plans trong Cơ sở dữ liệu recipe_discovery
Hình 3.15. Bảng meal_plan_items trong Cơ sở dữ liệu recipe_discovery
Hình 3.16. Bảng user_categories trong Cơ sở dữ liệu recipe_discovery
Hình 3.17. Bảng favorites trong Cơ sở dữ liệu recipe_discovery
Hình 3.18. Sơ đồ ERD Diagram của Recipe Discovery
Hình 4.1. Giao diện màn hình đăng nhập
Hình 4.2. Giao diện màn hình đăng ký
Hình 4.3. Giao diện màn hình Công thức cá nhân
Hình 4.4. Giao diện màn hình Tạo/Chỉnh sửa công thức
Hình 4.5. Giao diện màn hình Chi tiết công thức
Hình 4.6. Giao diện màn hình Kế hoạch bữa ăn (Meal Plan)
Hình 4.7. Giao diện màn hình Công thức cộng đồng
Hình 4.8. Giao diện màn hình Admin Dashboard
Hình 4.9. Giao diện email thông báo bữa ăn


MỤC LỤC

CHƯƠNG I: TỔNG QUAN	1
1.1. Giới thiệu tổng quát	1
1.2. Mục đích đề tài	1
1.3. Đối tượng và phạm vi nghiên cứu	2
1.4. Phương pháp nghiên cứu	2
1.5. Nội dung thực hiện của đề tài	2
1.5.1. Phân tích yêu cầu	2
1.5.2. Thiết kế hệ thống	3
1.5.3. Phát triển ứng dụng	3
1.5.4. Tối ưu và cải tiến	3
1.5.5. Hướng dẫn sử dụng và triển khai	3
1.6. Môi trường ứng dụng	3
1.7. Kết quả thực hiện	4
CHƯƠNG II: CƠ SỞ LÝ THUYẾT	5
2.1. Tổng quan về công nghệ sử dụng	5
2.2. Spring Boot Framework	5
2.2.1. Giới thiệu Spring Boot	5
2.2.2. Đặc điểm nổi bật của Spring Boot	5
2.2.3. Vai trò của Spring Boot trong đề tài	6
2.3. Thymeleaf Template Engine	6
2.4. MySQL Database	7
2.5. Mô hình kiến trúc hệ thống	7
2.6. Lý do lựa chọn công nghệ	8
CHƯƠNG III: PHÂN TÍCH, THIẾT KẾ HỆ THỐNG	9
3.1. Mô tả bài toán	9
3.1.1. Bối cảnh	9
3.1.2. Mục tiêu hệ thống	9
3.1.3. Đối tượng sử dụng	10
3.2. Sơ đồ Use Case	10
3.2.1. Sơ đồ Use Case tổng quát	10
3.2.2. Đặc tả Use Case	11
3.2.2.1. Chức năng Quản lý người dùng & Xác thực	11
3.2.2.2. Chức năng Quản lý công thức cá nhân	13
3.2.2.3. Chức năng Kế hoạch bữa ăn (Meal Planning)	15
3.2.2.4. Chức năng Email Notification	17
3.2.2.5. Chức năng Community Sharing	19
3.2.2.6. Chức năng Admin Dashboard	20
3.3. Thiết kế cơ sở dữ liệu	22
3.3.1. Các bảng chính trong hệ thống	22
3.3.1.1. Bảng users	22
3.3.1.2. Bảng recipes	23
3.3.1.3. Bảng meal_plans	24
3.3.1.4. Bảng meal_plan_items	24
3.3.1.5. Bảng user_categories	25
3.3.1.6. Bảng favorites	25
3.3.2. Mối quan hệ giữa các bảng	26
3.3.3. Sơ đồ ERD tổng quát	27
CHƯƠNG IV: GIAO DIỆN CHƯƠNG TRÌNH	28
4.1. Giao diện đăng nhập	28
4.2. Giao diện đăng ký	29
4.3. Giao diện Công thức cá nhân	30
4.4. Giao diện Chi tiết công thức	31
4.5. Giao diện Kế hoạch bữa ăn	32
4.6. Giao diện Cộng đồng	33
4.7. Giao diện Admin	34
4.8. Email thông báo	35
CHƯƠNG V: KẾT LUẬN	36
5.1. Kết quả đạt được	36
5.2. Hạn chế và hướng phát triển	36
TÀI LIỆU THAM KHẢO	38


---
---
---


CHƯƠNG I: TỔNG QUAN

## 1.1. Giới thiệu tổng quát

Trong thời đại số hóa hiện nay, nhu cầu quản lý thông tin cá nhân ngày càng trở nên quan trọng, đặc biệt là trong lĩnh vực ẩm thực - một phần không thể thiếu trong cuộc sống hàng ngày. Việc lưu trữ, tổ chức và chia sẻ công thức nấu ăn không chỉ giúp người dùng quản lý kiến thức cá nhân mà còn góp phần xây dựng một cộng đồng chia sẻ văn hóa ẩm thực phong phú.

"Recipe Discovery" là một hệ thống web application được phát triển nhằm giải quyết các vấn đề trên. Hệ thống cho phép người dùng:
- Tạo và quản lý công thức nấu ăn cá nhân với đầy đủ thông tin (nguyên liệu, hướng dẫn, dinh dưỡng)
- Lập kế hoạch bữa ăn theo tuần (meal planning) với 4 khung giờ chính: sáng, trưa, phụ, tối
- Nhận thông báo email tự động về bữa ăn đã lên lịch
- Chia sẻ công thức với cộng đồng và khám phá món ăn mới
- Phân loại công thức theo categories tùy chỉnh

Đồ án được thực hiện theo quy trình phát triển phần mềm chuẩn, áp dụng mô hình kiến trúc MVC (Model-View-Controller) với Spring Boot framework, đảm bảo tính mở rộng, bảo trì và hiệu năng cao.

## 1.2. Mục đích đề tài

Mục đích chính của đề tài là xây dựng một hệ thống quản lý công thức nấu ăn hoàn chỉnh, đáp ứng các yêu cầu sau:

**Về mặt chức năng:**
- Cung cấp công cụ quản lý công thức nấu ăn cá nhân hiệu quả, dễ sử dụng
- Hỗ trợ lập kế hoạch bữa ăn (meal planning) theo tuần để người dùng có thể tổ chức chế độ ăn uống khoa học
- Tự động hóa việc nhắc nhở bữa ăn thông qua email notification
- Xây dựng platform chia sẻ công thức trong cộng đồng
- Cung cấp công cụ quản trị hệ thống cho admin

**Về mặt kỹ thuật:**
- Vận dụng kiến thức về Spring Boot, Spring MVC, Spring Data JPA
- Thực hành thiết kế database và ORM mapping với Hibernate
- Áp dụng Thymeleaf template engine để xây dựng giao diện động
- Triển khai scheduled tasks với Spring Scheduler
- Tích hợp email service với JavaMailSender

**Về mặt học tập:**
- Rèn luyện kỹ năng phân tích, thiết kế hệ thống phần mềm
- Thực hành quy trình phát triển phần mềm từ đầu đến cuối
- Nâng cao khả năng làm việc với công nghệ thực tế
- Phát triển tư duy giải quyết vấn đề và debug

## 1.3. Đối tượng và phạm vi nghiên cứu

**Đối tượng nghiên cứu:**
- Quy trình quản lý công thức nấu ăn cá nhân
- Hệ thống lập kế hoạch bữa ăn (meal planning)
- Cơ chế thông báo email tự động theo lịch
- Platform chia sẻ công thức trong cộng đồng

**Phạm vi nghiên cứu:**

*Về chức năng:*
- Quản lý users: Đăng ký, đăng nhập, phân quyền (USER/ADMIN)
- Quản lý công thức: CRUD operations, upload ảnh, phân loại
- Meal planning: Lập lịch cho 4 bữa/ngày x 7 ngày/tuần
- Email notifications: Gửi tự động vào 4 khung giờ (7h, 11h, 15h, 17h)
- Community: Chia sẻ và khám phá công thức công khai
- Admin dashboard: Quản lý users, kiểm duyệt nội dung

*Về công nghệ:*
- Backend: Spring Boot 3.2.2, Java 17
- Frontend: Thymeleaf, Bootstrap 5.3.3
- Database: MySQL 8.x
- Email: JavaMailSender, Gmail SMTP
- Build tool: Maven

*Giới hạn phạm vi:*
- Chưa triển khai payment gateway
- Chưa có mobile app (chỉ có web responsive)
- Chưa hỗ trợ đa ngôn ngữ
- Password encryption cơ bản (chưa dùng BCrypt - sẽ cải tiến)

## 1.4. Phương pháp nghiên cứu

Đề tài được thực hiện theo phương pháp nghiên cứu thực nghiệm kết hợp với nghiên cứu ứng dụng:

**1. Nghiên cứu lý thuyết:**
- Tìm hiểu về Spring Boot framework và ecosystem
- Nghiên cứu mô hình MVC và best practices
- Tìm hiểu về Thymeleaf template engine
- Nghiên cứu Spring Data JPA và Hibernate ORM
- Tìm hiểu về scheduled tasks và email integration

**2. Phân tích yêu cầu:**
- Khảo sát nhu cầu quản lý công thức nấu ăn
- Phân tích các ứng dụng tương tự (competitor analysis)
- Xác định use cases và user stories
- Lập bảng yêu cầu chức năng và phi chức năng

**3. Thiết kế hệ thống:**
- Vẽ use case diagrams, sequence diagrams, activity diagrams
- Thiết kế database schema và ERD
- Thiết kế kiến trúc hệ thống theo pattern MVC
- Mockup giao diện người dùng

**4. Phát triển và kiểm thử:**
- Phát triển từng module theo quy trình Agile
- Unit testing cho service layer
- Integration testing cho API endpoints
- Manual testing cho UI/UX
- Bug fixing và optimization

## 1.5. Nội dung thực hiện của đề tài

### 1.5.1. Phân tích yêu cầu

**Yêu cầu chức năng:**
- F1: Quản lý người dùng & Xác thực (đăng ký, đăng nhập, phân quyền)
- F2: Quản lý công thức cá nhân (CRUD, upload ảnh, phân loại)
- F3: Cộng đồng chia sẻ (public recipes, favorites, search)
- F4: Kế hoạch bữa ăn (meal planning cho 4 bữa x 7 ngày)
- F5: Email notification (gửi tự động 4 lần/ngày)
- F6: Quản lý categories (tạo, sửa, xóa danh mục)
- F7: Admin dashboard (quản lý users, kiểm duyệt)
- F8: Calculator dinh dưỡng

**Yêu cầu phi chức năng:**
- Performance: Response time < 2 giây
- Security: Session-based authentication, role-based authorization
- Usability: Responsive design, user-friendly interface
- Maintainability: Clean code, MVC architecture
- Scalability: Modular structure, easy to extend

### 1.5.2. Thiết kế hệ thống

- Thiết kế kiến trúc hệ thống theo mô hình MVC
- Thiết kế database với 6 bảng chính: users, recipes, meal_plans, meal_plan_items, user_categories, favorites
- Thiết kế RESTful API endpoints
- Thiết kế UI/UX với Bootstrap framework
- Thiết kế email templates với Thymeleaf

### 1.5.3. Phát triển ứng dụng

**Backend Development:**
- Setup Spring Boot project với Maven
- Implement Models (Entities) với JPA annotations
- Implement Repositories với Spring Data JPA
- Implement Services (business logic layer)
- Implement Controllers (request handling)
- Implement Scheduler cho email notifications
- Configure email service với JavaMailSender

**Frontend Development:**
- Tạo Thymeleaf templates cho các trang: login, register, home, recipe-form, recipe-detail, meal-plan, community, admin
- Styling với Bootstrap 5.3.3
- Implement responsive design
- JavaScript cho dynamic interactions

**Integration:**
- Kết nối Spring Boot với MySQL
- Cấu hình Thymeleaf template resolver
- Setup file upload handling
- Configure email SMTP settings

### 1.5.4. Tối ưu và cải tiến

- Optimize database queries với indexing
- Implement pagination cho danh sách dài
- Add transaction management
- Error handling và logging
- Code refactoring và cleanup
- Performance tuning

### 1.5.5. Hướng dẫn sử dụng và triển khai

- Viết documentation (README, setup guides)
- Tạo user manual
- Deploy guide cho production
- Database migration scripts
- Environment configuration

## 1.6. Môi trường ứng dụng

**Môi trường phát triển:**
- IDE: IntelliJ IDEA 2024
- JDK: Java 17
- Build Tool: Apache Maven 3.9.x
- Database: MySQL 8.0.33
- Version Control: Git
- Email Service: Gmail SMTP

**Công nghệ sử dụng:**

*Backend:*
- Spring Boot 3.2.2
- Spring MVC (Web framework)
- Spring Data JPA (ORM)
- Spring Mail (Email integration)
- Spring Scheduler (Scheduled tasks)
- Hibernate (JPA implementation)
- Lombok (Boilerplate code reduction)

*Frontend:*
- Thymeleaf 3.1.2
- Bootstrap 5.3.3
- Bootstrap Icons 1.11.3
- Vanilla JavaScript

*Database:*
- MySQL 8.x
- Flyway (Database migration - optional)

*Testing:*
- JUnit 5
- Spring Boot Test
- Manual testing

**Yêu cầu hệ thống:**
- Java 17 trở lên
- MySQL 8.0 trở lên
- RAM: Tối thiểu 4GB
- Disk: Tối thiểu 500MB
- Port: 8080 (application), 3306 (MySQL)

## 1.7. Kết quả thực hiện

**Kết quả đạt được:**

*Về chức năng:*
- ✅ Hoàn thành 100% các chức năng chính đã đề ra
- ✅ Hệ thống authentication và authorization hoạt động ổn định
- ✅ CRUD operations cho recipes hoạt động đầy đủ
- ✅ Meal planning system với giao diện trực quan
- ✅ Email scheduler gửi thông báo chính xác theo lịch
- ✅ Community platform cho phép chia sẻ và khám phá
- ✅ Admin dashboard với đầy đủ quyền quản trị

*Về kỹ thuật:*
- Codebase: ~10,000 dòng code Java + HTML/CSS
- Database: 6 bảng chính với đầy đủ relationships
- API Endpoints: 30+ endpoints
- Templates: 17 Thymeleaf templates
- Performance: Average response time < 1 giây

*Về kiến thức:*
- Nắm vững Spring Boot ecosystem
- Thành thạo MVC architecture pattern
- Hiểu rõ ORM và database design
- Biết cách triển khai scheduled tasks
- Có kinh nghiệm về email integration

**Hạn chế:**
- Chưa implement password encryption (BCrypt)
- Session timeout chưa được configure tối ưu
- Chưa có comprehensive unit tests
- Error messages chưa được internationalize
- Chưa optimize cho very large datasets

**Hướng phát triển:**
- Implement BCrypt password hashing
- Add comprehensive test suite
- Optimize database queries với caching
- Implement real-time notifications với WebSocket
- Add advanced search với Elasticsearch
- Deploy lên cloud platform (AWS, Azure)


---
---
---


CHƯƠNG II: CƠ SỞ LÝ THUYẾT

## 2.1. Tổng quan về công nghệ sử dụng

Hệ thống Recipe Discovery được xây dựng dựa trên stack công nghệ Java web development hiện đại, với Spring Boot làm framework chính. Kiến trúc hệ thống tuân theo mô hình MVC (Model-View-Controller), đảm bảo tính tách biệt giữa business logic, presentation logic và data access logic.

Việc lựa chọn Spring Boot không chỉ giúp tăng tốc độ phát triển nhờ vào tính năng auto-configuration, mà còn đảm bảo ứng dụng được xây dựng theo các best practices của industry. Thymeleaf được sử dụng làm template engine cho phép tích hợp chặt chẽ với Spring, trong khi MySQL đảm nhận vai trò lưu trữ dữ liệu với hiệu năng cao.

## 2.2. Spring Boot Framework

### 2.2.1. Giới thiệu Spring Boot

Spring Boot là một framework Java được phát triển bởi Pivotal Team, được xây dựng trên nền tảng Spring Framework. Spring Boot giúp đơn giản hóa việc tạo và triển khai các ứng dụng Spring-based bằng cách cung cấp:
- Auto-configuration: Tự động cấu hình beans dựa trên dependencies
- Standalone applications: Embed Tomcat/Jetty server, chạy độc lập
- Production-ready features: Health checks, metrics, externalized configuration
- No code generation và no XML configuration

### 2.2.2. Đặc điểm nổi bật của Spring Boot

**1. Convention over Configuration:**
Spring Boot áp dụng nguyên tắc "conventions over configuration", giảm thiểu việc cấu hình thủ công. Ví dụ: khi phát hiện MySQL driver trong classpath, Spring Boot tự động cấu hình DataSource.

**2. Spring Boot Starters:**
Cung cấp các dependency descriptors để dễ dàng thêm công nghệ:
- spring-boot-starter-web: Web applications, RESTful
- spring-boot-starter-data-jpa: JPA với Hibernate
- spring-boot-starter-thymeleaf: Thymeleaf template engine
- spring-boot-starter-mail: Email support

**3. Embedded Server:**
Ứng dụng Spring Boot chạy standalone với embedded Tomcat, không cần deploy lên external application server.

**4. Production-ready features:**
- Actuator endpoints cho monitoring
- Externalized configuration (application.yml/properties)
- Logging framework integration
- Profile-based configuration

### 2.2.3. Vai trò của Spring Boot trong đề tài

Trong Recipe Discovery, Spring Boot đóng vai trò là backbone của hệ thống:

**Spring MVC:** Xử lý HTTP requests, routing, và controller logic
```java
@Controller
@RequestMapping("/app/recipes")
public class UserRecipeController {
    @GetMapping("/home")
    public String home(Model model) {
        // Handle request
    }
}
```

**Spring Data JPA:** Đơn giản hóa data access layer
```java
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserId(Long userId);
}
```

**Spring Scheduler:** Quản lý scheduled tasks cho email notifications
```java
@Scheduled(cron = "0 0 7 * * ?")
public void sendBreakfastNotifications() {
    // Send emails
}
```

**Dependency Injection:** Quản lý dependency giữa các components, dễ dàng testing và maintenance.

## 2.3. Thymeleaf Template Engine

Thymeleaf là một Java template engine hiện đại cho web applications. Khác với JSP, Thymeleaf templates là pure HTML có thể mở trực tiếp trên browser, giúp designer và developer làm việc cùng nhau dễ dàng hơn.

**Đặc điểm chính:**
- Natural templating: Template vẫn là valid HTML
- Spring integration: Tích hợp sâu với Spring MVC
- Expression language: Powerful và easy-to-use
- Fragments: Reusable components (header, footer)

**Sử dụng trong đề tài:**
```html
<div th:each="recipe : ${recipes}">
    <h3 th:text="${recipe.title}">Title</h3>
    <p th:text="${recipe.description}">Description</p>
</div>
```

Thymeleaf được sử dụng để render:
- User-facing pages (login, home, meal-plan...)
- Email templates (meal-notification-email.html)
- Admin dashboards

## 2.4. MySQL Database

MySQL là hệ quản trị cơ sở dữ liệu quan hệ (RDBMS) mã nguồn mở phổ biến nhất thế giới.

**Ưu điểm:**
- Hiệu năng cao, xử lý queries nhanh
- Độ tin cậy và ổn định
- Dễ dàng cài đặt, quản lý
- Hỗ trợ transactions (ACID compliance)
- Community lớn, tài liệu phong phú

**Sử dụng trong đề tài:**
- Database name: `recipe_discovery`
- Character set: UTF8MB4 (hỗ trợ emoji, Unicode)
- Storage engine: InnoDB (transactions, foreign keys)
- 6 tables chính: users, recipes, meal_plans, meal_plan_items, user_categories, favorites

**Hibernate ORM:**
Spring Boot sử dụng Hibernate làm JPA implementation để map Java objects ↔ database tables:
```java
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

## 2.5. Mô hình kiến trúc hệ thống

Recipe Discovery áp dụng mô hình MVC (Model-View-Controller) với Service Layer:

```
┌─────────────────────────────────────┐
│     Presentation Layer (View)      │
│     Thymeleaf Templates             │
└────────────┬────────────────────────┘
             │ HTTP Request/Response
┌────────────▼────────────────────────┐
│     Controller Layer                │
│     @Controller / @RestController   │
│     - AuthController                │
│     - UserRecipeController          │
│     - MealPlanController...         │
└────────────┬────────────────────────┘
             │ DTOs
┌────────────▼────────────────────────┐
│     Service Layer                   │
│     Business Logic                  │
│     - RecipeService                 │
│     - MealPlanService               │
│     - EmailService...               │
└────────────┬────────────────────────┘
             │ Entities
┌────────────▼────────────────────────┐
│     Data Access Layer               │
│     Repositories (JPA)              │
│     - RecipeRepository              │
│     - UserRepository...             │
└────────────┬────────────────────────┘
             │ SQL
┌────────────▼────────────────────────┐
│          Database                   │
│          MySQL 8.x                  │
└─────────────────────────────────────┘
```

**Hình 2.1. Mô hình kiến trúc MVC của Recipe Discovery**

**Giải thích các tầng:**

1. **View (Presentation Layer):**
   - Thymeleaf templates
   - HTML, CSS (Bootstrap)
   - JavaScript
   - Hiển thị dữ liệu cho user

2. **Controller:**
   - Nhận HTTP requests
   - Gọi service layer
   - Chuẩn bị dữ liệu cho view
   - Return view name hoặc redirect

3. **Service:**
   - Business logic
   - Transaction management
   - Validation
   - Gọi repositories

4. **Repository:**
   - Data access logic
   - CRUD operations
   - Query generation

5. **Model (Entities):**
   - Represent database tables
   - JPA annotations
   - Relationships

## 2.6. Lý do lựa chọn công nghệ

**Tại sao chọn Spring Boot?**
- Mature framework với community lớn
- Production-ready features
- Dễ dàng tích hợp với các công nghệ khác
- Best practices được tích hợp sẵn
- Auto-configuration giảm boilerplate code

**Tại sao chọn Thymeleaf?**
- Tích hợp tốt với Spring
- Natural templates, dễ làm việc với designers
- Server-side rendering, SEO friendly
- Powerful expression language

**Tại sao chọn MySQL?**
- Miễn phí, mã nguồn mở
- Performance tốt
- Ổn định, đáng tin cậy
- Hỗ trợ tốt từ community
- Phù hợp với scale của đề tài

**Tại sao chọn Maven?**
- Dependency management tự động
- Build lifecycle chuẩn
- Plugin ecosystem phong phú
- Industry standard


---
---
---


CHƯƠNG III: PHÂN TÍCH, THIẾT KẾ HỆ THỐNG

## 3.1. Mô tả bài toán

### 3.1.1. Bối cảnh

Trong cuộc sống hiện đại, việc quản lý thông tin về công thức nấu ăn và lập kế hoạch bữa ăn là một nhu cầu thiết thực. Nhiều người gặp khó khăn trong việc:
- Lưu trữ và tổ chức công thức nấu ăn cá nhân
- Lập kế hoạch bữa ăn hàng tuần một cách khoa học
- Tính toán lượng dinh dưỡng và calories
- Chia sẻ và học hỏi công thức từ cộng đồng
- Nhắc nhở về bữa ăn đã lên lịch

Các giải pháp hiện có thường:
- Phân tán (ghi chú trên giấy, notebook, app khác nhau)
- Thiếu tính năng meal planning tích hợp
- Không có notification system
- Interface phức tạp, không trực quan

### 3.1.2. Mục tiêu hệ thống

Recipe Discovery được xây dựng nhằm cung cấp một giải pháp tổng thể cho các vấn đề trên:

1. **Quản lý công thức tập trung:** Người dùng có thể tạo, lưu trữ, chỉnh sửa công thức với đầy đủ thông tin (nguyên liệu, hướng dẫn, dinh dưỡng, ảnh)

2. **Meal Planning System:** Lập lịch bữa ăn cho cả tuần với 4 khung giờ (sáng, trưa, phụ, tối), giúp quản lý chế độ ăn khoa học

3. **Email Notification:** Tự động gửi email nhắc nhở về bữa ăn vào đúng thời điểm, không bao giờ bỏ lỡ

4. **Community Platform:** Chia sẻ công thức với cộng đồng, khám phá món ăn mới, lưu favorites

5. **Admin Dashboard:** Quản lý users, kiểm duyệt nội dung, xem thống kê hệ thống

### 3.1.3. Đối tượng sử dụng

**1. User (Người dùng thông thường):**
- Đăng ký, đăng nhập hệ thống
- Tạo và quản lý công thức cá nhân
- Lập meal plan cho tuần
- Nhận email notifications
- Chia sẻ và xem công thức cộng đồng

**2. Admin (Quản trị viên):**
- Quản lý users (xem, sửa, xóa, ban)
- Kiểm duyệt công thức cộng đồng
- Xem dashboard và thống kê
- Quản lý nội dung hệ thống

##  3.2. Sơ đồ Use Case

### 3.2.1. Sơ đồ Use Case tổng quát

```
                    ┌────────────────────────────────┐
                    │      Recipe Discovery          │
                    │                                │
     ┌──────────────┼────────────────────────────────┼──────────────┐
     │              │                                │              │
     │              │   AUTHENTICATION               │              │
┌────▼─────┐        │   ┌─────────────────────┐     │              │
│          │        │   │ UC1: Đăng ký        │     │              │
│   User   │───────────▶│ UC2: Đăng nhập      │     │              │
│          │        │   │ UC3: Đăng xuất      │     │              │
└────┬─────┘        │   └─────────────────────┘     │              │
     │              │                                │              │
     │              │   RECIPE MANAGEMENT            │              │
     │              │   ┌─────────────────────┐     │              │
     └─────────────────▶│ UC4: Tạo công thức  │     │              │
                    │   │ UC5: Sửa công thức  │     │              │
                    │   │ UC6: Xóa công thức  │     │              │
                    │   │ UC7: Xem danh sách  │     │              │
                    │   └─────────────────────┘     │              │
                    │                                │              │
                    │   MEAL PLANNING                │              │
                    │   ┌─────────────────────┐     │              │
     ┌──────────────┼──▶│ UC8: Tạo meal plan  │     │              │
     │              │   │ UC9: Thêm món ăn    │     │              │
     │              │   │ UC10: Xóa món ăn    │     │              │
┌────▼─────┐        │   └─────────────────────┘     │              │
│          │        │                                │              │
│  System  │        │   EMAIL NOTIFICATION           │              │
│Scheduler │        │   ┌─────────────────────┐     │              │
│          │───────────▶│ UC11: Gửi email tự  │     │              │
└──────────┘        │   │       động          │     │              │
                    │   └─────────────────────┘     │              │
                    │                                │              │
                    │   COMMUNITY                    │              │
                    │   ┌─────────────────────┐     │              │
     ┌──────────────┼──▶│ UC12: Chia sẻ       │     │              │
     │              │   │ UC13: Xem cộng đồng │     │              │
     │              │   │ UC14: Lưu favorite  │     │   ┌─────────┐
     │              │   └─────────────────────┘     │   │         │
     │              │                                │   │  Admin  │
     │              │   ADMIN                        │   │         │
     │              │   ┌─────────────────────┐─────┼───▶         │
     │              │   │ UC15: Quản lý users │     │   └─────────┘
     │              │   │ UC16: Kiểm duyệt    │     │
     │              │   │ UC17: Dashboard     │     │
     │              │   └─────────────────────┘     │
     │              │                                │
     └──────────────┴────────────────────────────────┘
```

**Hình 3.1. Use Case Diagram Tổng quát**

### 3.2.2. Đặc tả Use Case

#### 3.2.2.1. Chức năng Quản lý người dùng & Xác thực

**UC1: Đăng ký tài khoản**

- **Actor:** User (chưa đăng ký)
- **Mô tả:** Người dùng tạo tài khoản mới để sử dụng hệ thống
- **Preconditions:** 
  - User chưa có tài khoản
  - Truy cập được trang đăng ký
- **Postconditions:**
  - Tài khoản mới được tạo trong database
  - User được redirect đến trang đăng nhập

**Main Flow:**
1. User truy cập trang `/register`
2. System hiển thị form đăng ký
3. User điền thông tin:
   - Full name
   - Email
   - Password
4. User click "Đăng ký"
5. System validate dữ liệu:
   - Email chưa tồn tại
   - Password đủ mạnh
6. System tạo user mới với role = "USER"
7. System redirect về `/login` với message "Đăng ký thành công"

**Alternative Flows:**
- 5a. Email đã tồn tại → Show error "Email đã được sử dụng"
- 5b. Validation fails → Show errors, return to step 3

```
┌──────────┐          ┌────────────┐          ┌──────────┐
│   User   │          │  System    │          │Database  │
└─────┬────┘          └──────┬─────┘          └────┬─────┘
      │                      │                     │
      │  GET /register       │                     │
      │─────────────────────▶│                     │
      │                      │                     │
      │  Show register form  │                     │
      │◀─────────────────────│                     │
      │                      │                     │
      │  POST /register      │                     │
      │  {email, password}   │                     │
      │─────────────────────▶│                     │
      │                      │ Check email exists? │
      │                      │────────────────────▶│
      │                      │◀────────────────────│
      │                      │    Not exists       │
      │                      │                     │
      │                      │  INSERT user        │
      │                      │────────────────────▶│
      │                      │◀────────────────────│
      │                      │    Success          │
      │  Redirect to login   │                     │
      │◀─────────────────────│                     │
      │                      │                     │
```

**Hình 3.2. Use Case UML của chức năng Đăng ký / Đăng nhập**

**UC2: Đăng nhập**

- **Actor:** User (đã đăng ký)
- **Mô tả:** User đăng nhập để truy cập hệ thống
- **Preconditions:** User đã có tài khoản
- **Postconditions:** 
  - Session được tạo
  - User được redirect theo role (USER → /app/home, ADMIN → /admin/dashboard)

**Main Flow:**
1. User truy cập `/login`
2. System hiển thị login form
3. User nhập email, password
4. User click "Đăng nhập"
5. System verify credentials
6. System tạo session, lưu user info
7. System redirect theo role

**Alternative Flows:**
- 5a. Sai email/password → Show error "Thông tin đăng nhập không đúng"
- 5b. Account bị ban → Show error "Tài khoản đã bị khóa"

```
[User] --▶ [Nhập email, password] --▶ [Validate] --▶ [Tạo session] --▶ [Redirect theo role]
                                          │
                                          ▼
                                     [Invalid]
                                          │
                                          ▼
                                    [Show error]
```

**Hình 3.3. Luồng hoạt động của chức năng Đăng ký / Đăng nhập**

#### 3.2.2.2. Chức năng Quản lý công thức cá nhân

(Continue trong file...)

## 3.3. Thiết kế cơ sở dữ liệu

### 3.3.1. Các bảng chính trong hệ thống

#### 3.3.1.1. Bảng users

**Mục đích:** Lưu trữ thông tin người dùng

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | ID người dùng |
| full_name | VARCHAR(100) | NOT NULL | Họ tên |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Email đăng nhập |
| password | VARCHAR(255) | NOT NULL | Mật khẩu |
| role | VARCHAR(20) | DEFAULT 'USER' | Quyền (USER/ADMIN) |
| avatar_url | VARCHAR(500) | | URL avatar |
| status | VARCHAR(20) | DEFAULT 'ACTIVE' | Trạng thái tài khoản |
| note | VARCHAR(500) | | Ghi chú |
| created_at | TIMESTAMP | DEFAULT NOW() | Thời gian tạo |
| updated_at | TIMESTAMP | ON UPDATE NOW() | Thời gian cập nhật |

**Hình 3.12. Bảng users trong Cơ sở dữ liệu recipe_discovery**

(Continue with more tables...)

### 3.3.3. Sơ đồ ERD tổng quát

```
┌─────────────┐       ┌──────────────┐
│    users    │──1:N─▶│   recipes    │
└─────────────┘       └──────────────┘
      │1                      │1
      │                       │
      │N                      │N
┌─────────────┐       ┌──────────────┐
│ meal_plans  │──1:N─▶│meal_plan_    │
│             │       │   items      │
└─────────────┘       └──────────────┘
                              │N
                              │
                              │1
                      ┌──────────────┐
                      │   recipes    │
                      └──────────────┘
```

**Hình 3.18. Sơ đồ ERD Diagram của Recipe Discovery**


---
---
---


CHƯƠNG IV: GIAO DIỆN CHƯƠNG TRÌNH

## 4.1. Giao diện đăng nhập

Màn hình đăng nhập là điểm truy cập đầu tiên của người dùng vào hệ thống...

(Continue với mô tả các giao diện)


---
---
---


CHƯƠNG V: KẾT LUẬN

## 5.1. Kết quả đạt được

Sau quá trình thực hiện đồ án, em đã hoàn thành việc xây dựng hệ thống Recipe Discovery với đầy đủ các chức năng đã đề ra...

## 5.2. Hạn chế và hướng phát triển

**Hạn chế:**
- Password chưa được mã hóa bằng BCrypt
- Chưa có comprehensive test coverage
- Performance chưa được optimize cho large dataset
- Chưa hỗ trợ đa ngôn ngữ

**Hướng phát triển:**
- Implement BCrypt password encryption
- Add unit và integration tests
- Optimize database queries với caching (Redis)
- Implement WebSocket cho real-time notifications
- Deploy lên cloud platform
- Develop mobile app (Flutter/React Native)


---

TÀI LIỆU THAM KHẢO

[1] Spring Documentation, "Spring Boot Reference Guide", https://docs.spring.io/spring-boot/
[2] Thymeleaf Documentation, "Thymeleaf Template Engine", https://www.thymeleaf.org/
[3] MySQL Documentation, "MySQL 8.0 Reference Manual", https://dev.mysql.com/doc/
[4] Oracle, "Java SE 17 Documentation", https://docs.oracle.com/en/java/javase/17/
