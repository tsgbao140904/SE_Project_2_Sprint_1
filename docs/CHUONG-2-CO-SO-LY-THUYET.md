# CHƯƠNG II: CƠ SỞ LÝ THUYẾT

## 2.1. Tổng quan về công nghệ sử dụng

Để xây dựng hệ thống Recipe Discovery đáp ứng yêu cầu về tính ổn định, khả năng mở rộng và dễ bảo trì, đề tài lựa chọn sử dụng mô hình phát triển web application hiện đại với kiến trúc MVC (Model-View-Controller) phân tách rõ ràng giữa giao diện người dùng, logic xử lý và dữ liệu. Cụ thể, hệ thống sử dụng Spring Boot Framework cho phía backend, Thymeleaf Template Engine cho phía frontend và MySQL cho cơ sở dữ liệu.

Mô hình này giúp tăng tính module hóa trong quá trình phát triển, đồng thời tăng khả năng tái sử dụng code, dễ dàng kiểm thử và mở rộng hệ thống trong tương lai. Spring Boot cung cấp khả năng auto-configuration giảm thiểu cấu hình thủ công, trong khi Thymeleaf hỗ trợ server-side rendering tích hợp chặt chẽ với Spring MVC.

## 2.2. Spring Boot Framework

### 2.2.1. Giới thiệu Spring Boot

Spring Boot là một framework Java mã nguồn mở được phát triển bởi Pivotal Team (hiện thuộc VMware), được xây dựng trên nền tảng Spring Framework. Spring Boot ra đời nhằm đơn giản hóa việc tạo và triển khai các ứng dụng Spring-based production-ready bằng cách cung cấp cơ chế auto-configuration, embedded server và các starter dependencies.

Spring Boot áp dụng nguyên tắc "convention over configuration" (ưu tiên quy ước hơn cấu hình), giúp lập trình viên tập trung vào business logic thay vì phải cấu hình phức tạp. Framework tự động phát hiện các dependencies trong classpath và tự động cấu hình các beans phù hợp.

### 2.2.2. Đặc điểm nổi bật của Spring Boot

Spring Boot có các đặc điểm nổi bật sau:

**Auto-Configuration:** Spring Boot tự động cấu hình ứng dụng dựa trên các dependencies có trong classpath. Ví dụ: khi phát hiện MySQL driver, Spring Boot tự động cấu hình DataSource bean.

**Standalone Applications:** Ứng dụng Spring Boot chạy độc lập với embedded Tomcat/Jetty server, không cần deploy lên external application server. Chỉ cần chạy lệnh `java -jar application.jar`.

**Spring Boot Starters:** Cung cấp các dependency descriptors giúp dễ dàng thêm công nghệ vào project:
- `spring-boot-starter-web`: Web applications, RESTful APIs
- `spring-boot-starter-data-jpa`: JPA với Hibernate ORM
- `spring-boot-starter-thymeleaf`: Thymeleaf template engine
- `spring-boot-starter-mail`: Email support với JavaMailSender

**Production-Ready Features:** Tích hợp sẵn các tính năng cho production như health checks, metrics, externalized configuration (application.yml), logging framework integration.

**No Code Generation & No XML:** Không sinh code tự động và không yêu cầu cấu hình XML phức tạp, mọi cấu hình đều thông qua annotations và Java-based configuration.

### 2.2.3. Vai trò của Spring Boot trong đề tài

Trong đề tài này, Spring Boot được sử dụng để:

**Spring MVC:** Xử lý HTTP requests, routing và controller logic. Các `@Controller` classes nhận requests, gọi service layer và trả về view names hoặc redirects.

**Spring Data JPA:** Đơn giản hóa data access layer thông qua interface-based repositories. Tự động sinh ra implementation cho các CRUD operations và custom queries.

**Spring Scheduler:** Quản lý scheduled tasks để gửi email notifications tự động. Sử dụng `@Scheduled` annotation với cron expressions để trigger methods theo lịch.

**Spring Mail:** Tích hợp JavaMailSender để gửi email thông báo bữa ăn. Hỗ trợ SMTP protocol với Gmail.

**Dependency Injection & IoC:** Quản lý dependencies giữa các components (Controllers, Services, Repositories) thông qua @Autowired, giúp code dễ test và maintain.

**Transaction Management:** Quản lý database transactions tự động với `@Transactional` annotation, đảm bảo ACID properties.

## 2.3. Thymeleaf Template Engine

### 2.3.1. Giới thiệu Thymeleaf

Thymeleaf là một Java template engine hiện đại dành cho web applications và standalone environments. Khác với JSP (JavaServer Pages), Thymeleaf templates là pure HTML và có thể mở trực tiếp trên browser mà không cần chạy server, giúp designers và developers làm việc song song dễ dàng hơn.

Thymeleaf được thiết kế để tích hợp chặt chẽ với Spring Framework, đặc biệt là Spring MVC. Template engine này hỗ trợ natural templating - templates vẫn là valid HTML5, cho phép xem prototype trước khi chạy server.

### 2.3.2. Đặc điểm nổi bật của Thymeleaf

**Natural Templating:** Templates là HTML hợp lệ, có thể mở trực tiếp trên browser để xem layout mà không cần run server. Các attributes đặc biệt (th:*) bị browser ignore khi view static.

**Spring Integration:** Tích hợp sâu với Spring MVC:
- Truy cập Spring beans thông qua `@{...}` syntax
- Form binding với `th:object` và `th:field`
- Message internationalization với `#{...}`
- URL handling với `@{/path}` tự động thêm context path

**Expression Language:** Powerful expression language hỗ trợ:
- Variable expressions: `${...}` (truy cập model attributes)
- Selection expressions: `*{...}` (truy cập selected object fields)
- Message expressions: `#{...}` (i18n messages)
- Link expressions: `@{...}` (URL building)

**Fragments & Layouts:** Hỗ trợ tái sử dụng code với fragments (header, footer, sidebar) và template layouts, giảm duplicate code.

### 2.3.3. Vai trò của Thymeleaf trong đề tài

Trong đề tài, Thymeleaf được sử dụng để:

**Render User-Facing Pages:** Tạo các trang web động cho users: login, register, home, recipe-form, meal-plan, community.

**Render Email Templates:** Tạo HTML email templates cho meal notifications. Template `meal-notification-email.html` được render với context chứa user và recipe info.

**Data Binding:** Hiển thị dữ liệu từ model:
```html
<div th:each="recipe : ${recipes}">
    <h3 th:text="${recipe.title}">Recipe Title</h3>
    <p th:text="${recipe.description}">Description</p>
</div>
```

**Form Handling:** Xử lý forms với validation:
```html
<form th:action="@{/app/recipes}" th:object="${recipeDTO}" method="post">
    <input type="text" th:field="*{title}" />
    <span th:if="${#fields.hasErrors('title')}" th:errors="*{title}"></span>
</form>
```

**Conditional Rendering:** Hiển thị có điều kiện:
```html
<div th:if="${user.role == 'ADMIN'}">
    <a th:href="@{/admin/dashboard}">Admin Panel</a>
</div>
```

## 2.4. MySQL Database

### 2.4.1. Giới thiệu MySQL

MySQL là một hệ quản trị cơ sở dữ liệu quan hệ (RDBMS - Relational Database Management System) mã nguồn mở phổ biến nhất thế giới. MySQL được phát triển bởi Oracle Corporation, sử dụng ngôn ngữ SQL (Structured Query Language) để truy vấn và quản lý dữ liệu.

MySQL là nền tảng database cho nhiều ứng dụng web lớn như Facebook, Twitter, YouTube, và được tích hợp trong stack LAMP (Linux, Apache, MySQL, PHP/Python/Perl).

### 2.4.2. Đặc điểm nổi bật của MySQL

**Performance:** Hiệu năng cao với khả năng xử lý queries nhanh chóng. Hỗ trợ indexing (B-Tree, Hash) để tăng tốc độ truy vấn.

**Reliability & Stability:** Độ tin cậy cao, đã được kiểm chứng qua nhiều năm sử dụng trong production. Hỗ trợ replication và clustering cho high availability.

**ACID Compliance:** Đảm bảo tính Atomicity, Consistency, Isolation, Durability cho transactions khi sử dụng InnoDB storage engine.

**Scalability:** Có khả năng scale từ ứng dụng nhỏ đến hệ thống lớn với hàng triệu records. Hỗ trợ partitioning và sharding.

**Cross-Platform:** Chạy trên nhiều hệ điều hành: Windows, Linux, macOS.

**Open Source:** Miễn phí, mã nguồn mở với cộng đồng lớn và tài liệu phong phú.

### 2.4.3. Vai trò của MySQL trong đề tài

Trong đề tài, MySQL được sử dụng để:

**Lưu trữ dữ liệu ứng dụng:** Database `recipe_discovery` chứa 6 bảng chính:
- `users`: Thông tin người dùng, authentication
- `recipes`: Công thức nấu ăn với đầy đủ thông tin
- `meal_plans`: Kế hoạch bữa ăn theo tuần
- `meal_plan_items`: Chi tiết từng món trong meal plan
- `user_categories`: Danh mục tùy chỉnh của users
- `favorites`: Công thức yêu thích

**Đảm bảo tính toàn vẹn dữ liệu:** Sử dụng foreign keys để maintain referential integrity:
```sql
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
```

**Tối ưu performance:** Sử dụng indexes trên các columns thường query:
```sql
INDEX idx_user_id (user_id),
INDEX idx_is_public (is_public),
INDEX idx_created_at (created_at)
```

**Hỗ trợ transactions:** Sử dụng InnoDB engine cho ACID transactions, đảm bảo data consistency khi có nhiều operations đồng thời.

**Character Set hỗ trợ Unicode:** Sử dụng `utf8mb4` character set và `utf8mb4_unicode_ci` collation để hỗ trợ tiếng Việt và emoji.

### 2.4.4. Hibernate ORM

Spring Boot sử dụng Hibernate làm JPA (Java Persistence API) implementation để map Java objects với database tables:

**Entity Mapping:**
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

**Automatic Schema Generation:** Hibernate có thể tự động tạo database schema từ entities (ddl-auto: create, update, validate).

**Query Generation:** JPA repositories tự động sinh SQL queries từ method names:
```java
List<Recipe> findByUserId(Long userId);
// Generated SQL: SELECT * FROM recipes WHERE user_id = ?
```

## 2.5. Mô hình kiến trúc hệ thống

Hệ thống Recipe Discovery được xây dựng theo mô hình **MVC (Model-View-Controller)** với **Service Layer** và **Repository Layer**, tuân thủ kiến trúc phân tầng (Layered Architecture):

### 2.5.1. Presentation Layer (View)

**Thành phần:** Thymeleaf Templates, HTML, CSS (Bootstrap), JavaScript

**Chức năng:** 
- Hiển thị giao diện người dùng
- Nhận input từ user thông qua forms, buttons, links
- Render động dữ liệu từ model

**Templates chính:** 17 templates bao gồm login.html, home.html, recipe-form.html, meal-plan.html, community.html...

### 2.5.2. Controller Layer

**Thành phần:** Spring MVC Controllers (@Controller/@RestController)

**Chức năng:**
- Nhận HTTP requests từ browser
- Validate input data
- Gọi service layer để xử lý business logic
- Chuẩn bị model data cho view
- Return view name hoặc redirect

**Controllers chính:** AuthController, UserRecipeController, MealPlanController, CommunityController, AdminController...

### 2.5.3. Service Layer

**Thành phần:** Service classes (@Service)

**Chức năng:**
- Chứa business logic của application
- Quản lý transactions với @Transactional
- Validate business rules
- Orchestrate multiple repository calls
- Transform entities ↔ DTOs

**Services chính:** RecipeService, MealPlanService, EmailService, AuthService, UserService...

### 2.5.4. Repository Layer (Data Access)

**Thành phần:** Spring Data JPA Repositories

**Chức năng:**
- CRUD operations
- Custom queries
- Query generation từ method names
- Không chứa business logic

**Repositories chính:** RecipeRepository, UserRepository, MealPlanRepository, MealPlanItemRepository...

### 2.5.5. Model Layer

**Thành phần:** JPA Entities (@Entity)

**Chức năng:**
- Represent database tables
- Define relationships (@OneToMany, @ManyToOne, @ManyToMany)
- Map Java fields ↔ database columns

**Entities chính:** User, Recipe, MealPlan, MealPlanItem, UserCategory...

### 2.5.6. Database Layer

**Thành phần:** MySQL Database

**Chức năng:**
- Lưu trữ dữ liệu persistent
- Enforce constraints, foreign keys
- Execute SQL queries từ Hibernate

### 2.5.7. Luồng xử lý request

Quá trình hoạt động điển hình (ví dụ: Tạo recipe mới):

1. **User** điền form tại `/app/recipes/create` và submit
2. **Browser** gửi POST request đến server
3. **Controller** (UserRecipeController) nhận request:
   - Validate input
   - Gọi RecipeService.create(recipeDTO)
4. **Service** (RecipeService) xử lý:
   - Handle file upload (save image)
   - Map DTO to Entity
   - Apply business rules
   - Gọi RecipeRepository.save(recipe)
5. **Repository** (RecipeRepository):
   - JPA/Hibernate sinh SQL INSERT
   - Execute SQL trên MySQL
6. **MySQL** lưu record, return generated ID
7. **Repository** return saved entity
8. **Service** return entity to Controller
9. **Controller** redirect to `/app/recipes/{id}`
10. **View** render recipe-detail.html
11. **Browser** hiển thị kết quả

```
┌─────────┐
│  User   │
└────┬────┘
     │ HTTP Request
     ▼
┌─────────────────────┐
│  Controller Layer   │ ← Validate, Route
└────┬────────────────┘
     │ DTOs
     ▼
┌─────────────────────┐
│  Service Layer      │ ← Business Logic
└────┬────────────────┘
     │ Entities
     ▼
┌─────────────────────┐
│  Repository Layer   │ ← Data Access
└────┬────────────────┘
     │ SQL
     ▼
┌─────────────────────┐
│  MySQL Database     │
└─────────────────────┘
```

**Hình 2.1. Mô hình kiến trúc MVC của Recipe Discovery**

*(Xem chi tiết diagram PlantUML trong file UML-DIAGRAMS.md)*

### 2.5.8. Component bổ sung

**Scheduler Component:**
- MealPlanNotificationScheduler với 4 cron jobs
- Chạy tự động vào 7h, 11h, 15h, 17h mỗi ngày
- Gọi EmailService để gửi notifications

**Interceptor Component:**
- AuthInterceptor để kiểm tra authentication
- Intercept tất cả requests trước khi đến controller
- Redirect về /login nếu chưa đăng nhập

**Configuration Classes:**
- WebConfig: Cấu hình static resources, interceptors
- Application.yml: Cấu hình datasource, JPA, mail settings

## 2.6. Lý do lựa chọn công nghệ

Việc lựa chọn Spring Boot, Thymeleaf và MySQL cho đề tài dựa trên các lý do sau:

### 2.6.1. Về Spring Boot Framework

**Enterprise-level framework:** Spring Boot là industry standard cho Java web development, được sử dụng rộng rãi trong các doanh nghiệp lớn.

**Mature ecosystem:** Spring ecosystem rất phong phú với nhiều modules (Security, Data, Cloud...), dễ dàng mở rộng trong tương lai.

**Auto-configuration:** Giảm đáng kể thời gian setup và configuration, cho phép tập trung vào business logic.

**Production-ready:** Tích hợp sẵn các tính năng cần thiết cho production như logging, monitoring, health checks.

**Learning value:** Học Spring Boot giúp sinh viên nắm vững các concepts quan trọng như Dependency Injection, AOP, Transaction Management.

### 2.6.2. Về Thymeleaf Template Engine

**Spring integration:** Tích hợp chặt chẽ với Spring MVC, dễ dàng access Spring beans và model attributes.

**Natural templates:** Templates là HTML hợp lệ, designers có thể làm việc song song với developers.

**Server-side rendering:** SEO-friendly, content được render trước khi gửi về browser.

**No JavaScript framework dependency:** Không cần học thêm framework frontend phức tạp (React, Angular), phù hợp với scope học phần.

### 2.6.3. Về MySQL Database

**Popularity:** MySQL là RDBMS phổ biến nhất, tài liệu và community support rất tốt.

**Free & Open Source:** Miễn phí, không có chi phí licensing.

**Performance:** Đủ hiệu năng cho ứng dụng vừa và nhỏ như Recipe Discovery.

**Compatibility:** JPA/Hibernate hỗ trợ tốt MySQL, dễ dàng switch sang PostgreSQL nếu cần.

**Học thuật:** Phù hợp để học về RDBMS, SQL queries, normalization, indexing.

### 2.6.4. Về kiến trúc MVC

**Separation of concerns:** Tách biệt rõ ràng giữa presentation, business logic và data access.

**Maintainability:** Dễ bảo trì, mỗi layer có responsibility riêng biệt.

**Testability:** Dễ dàng test từng layer độc lập với unit tests và integration tests.

**Scalability:** Có thể scale horizontal bằng cách deploy nhiều instances (stateless).

**Industry standard:** MVC là architecture pattern được sử dụng rộng rãi, học được apply vào công việc thực tế.

### 2.6.5. So sánh với alternatives

**Spring Boot vs Node.js/Express:**
- Spring Boot: Strongly-typed, enterprise features built-in, better for large projects
- Node.js: Loosely-typed, faster development cho small projects
- Lựa chọn Spring Boot để học về Java enterprise development

**Thymeleaf vs React/Angular:**
- Thymeleaf: Server-side rendering, simpler, no build process
- React/Angular: Client-side rendering, more interactive, steeper learning curve
- Lựa chọn Thymeleaf vì phù hợp với thời gian học phần, tập trung backend

**MySQL vs PostgreSQL:**
- MySQL: Simpler, faster cho read-heavy workloads
- PostgreSQL: More features, better cho complex queries
- Lựa chọn MySQL vì phổ biến hơn, dễ học

### 2.6.6. Kết luận

Tổ hợp công nghệ **Spring Boot + Thymeleaf + MySQL** là lựa chọn phù hợp nhất cho đề tài vì:
- Đáp ứng đầy đủ yêu cầu chức năng của hệ thống Recipe Discovery
- Phù hợp với trình độ và thời gian thực hiện của sinh viên
- Cung cấp kiến thức nền tảng vững chắc về web development
- Dễ triển khai, bảo trì và mở rộng trong tương lai
- Có giá trị thực tế cao, apply được vào công việc sau này

---

**Tổng kết:** Chương II đã trình bày cơ sở lý thuyết về các công nghệ được sử dụng trong đề tài, bao gồm Spring Boot Framework, Thymeleaf Template Engine, MySQL Database, mô hình kiến trúc MVC và lý do lựa chọn công nghệ. Chương tiếp theo sẽ đi sâu vào phân tích và thiết kế hệ thống.
