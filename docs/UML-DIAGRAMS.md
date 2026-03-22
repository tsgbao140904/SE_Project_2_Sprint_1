# UML DIAGRAMS - RECIPE DISCOVERY
## Tất cả các sơ đồ PlantUML cho báo cáo

> **Hướng dẫn sử dụng:**
> 1. Copy mã PlantUML
> 2. Paste vào https://www.plantuml.com/plantuml/uml/ để generate diagram
> 3. Download PNG/SVG để đưa vào báo cáo Word

---

## CHƯƠNG 2: CƠ SỞ LÝ THUYẾT

### Hình 2.1. Mô hình kiến trúc MVC của Recipe Discovery

```plantuml
@startuml
!define RECTANGLE class

skinparam backgroundColor #FFFFFF
skinparam roundcorner 10
skinparam shadowing false

package "Presentation Layer (View)" #E8F5E9 {
  rectangle "Thymeleaf Templates" as View {
    component "login.html"
    component "home.html"
    component "recipe-form.html"
    component "meal-plan.html"
    component "community.html"
  }
  
  rectangle "Frontend Assets" as Assets {
    component "Bootstrap 5.3.3"
    component "JavaScript"
    component "CSS"
  }
}

package "Controller Layer" #FFF3E0 {
  rectangle "Controllers" as Controllers {
    component "AuthController" as AC
    component "UserRecipeController" as URC
    component "MealPlanController" as MPC
    component "CommunityController" as CC
    component "AdminController" as ADC
  }
}

package "Service Layer" #E3F2FD {
  rectangle "Business Logic" as Services {
    component "AuthService" as AS
    component "RecipeService" as RS
    component "MealPlanService" as MPS
    component "EmailService" as ES
    component "UserService" as US
  }
}

package "Data Access Layer" #F3E5F5 {
  rectangle "Repositories" as Repos {
    component "UserRepository" as UR
    component "RecipeRepository" as RR
    component "MealPlanRepository" as MPR
    component "MealPlanItemRepository" as MPIR
  }
}

database "MySQL Database" as DB {
  frame "Tables" {
    storage "users"
    storage "recipes"
    storage "meal_plans"
    storage "meal_plan_items"
  }
}

View -down-> Controllers : "HTTP Request"
Controllers -up-> View : "Model + View Name"
Controllers -down-> Services : "DTOs"
Services -up-> Controllers : "Entities/Results"
Services -down-> Repos : "Entities"
Repos -up-> Services : "Entities"
Repos -down-> DB : "SQL"
DB -up-> Repos : "Result Set"

note right of View
  - Render HTML
  - User Interface
  - Bootstrap styling
end note

note right of Controllers
  - Handle HTTP
  - Validation
  - Request mapping
end note

note right of Services
  - Business rules
  - Transactions
  - Orchestration
end note

note right of Repos
  - CRUD operations
  - Query generation
  - Data mapping
end note

@enduml
```

### Hình 2.2. Technology Stack của hệ thống

```plantuml
@startuml
!theme vibrant

skinparam backgroundColor #FAFAFA
skinparam rectangleBorderColor #333333
skinparam rectangleFontSize 12

title Recipe Discovery - Technology Stack

rectangle "Frontend Layer" #90CAF9 {
  rectangle "Thymeleaf 3.1.2" as TH
  rectangle "Bootstrap 5.3.3" as BS
  rectangle "JavaScript ES6" as JS
  rectangle "Bootstrap Icons" as BI
}

rectangle "Backend Layer" #81C784 {
  rectangle "Spring Boot 3.2.2" as SB {
    rectangle "Spring MVC" as MVC
    rectangle "Spring Data JPA" as JPA
    rectangle "Spring Mail" as MAIL
    rectangle "Spring Scheduler" as SCHED
  }
  rectangle "Java 17" as JAVA
  rectangle "Lombok" as LOM
}

rectangle "Persistence Layer" #FFB74D {
  rectangle "Hibernate ORM" as HIB
  rectangle "MySQL Connector" as CON
}

rectangle "Database Layer" #E57373 {
  database "MySQL 8.0" as DB
}

rectangle "Build & Tools" #BA68C8 {
  rectangle "Maven 3.9" as MVN
  rectangle "IntelliJ IDEA" as IDE
  rectangle "Git" as GIT
}

rectangle "Email Service" #4DD0E1 {
  cloud "Gmail SMTP" as SMTP
}

' Relationships
TH -down-> MVC : "Server-side\nrendering"
BS -down-> TH : "Styling"
JS -down-> TH : "Interactions"

SB -down-> JAVA : "Built on"
MVC -down-> JPA
JPA -down-> HIB
MAIL -down-> SMTP : "Send emails"
SCHED -right-> MAIL : "Triggers"

HIB -down-> CON
CON -down-> DB : "JDBC"

MVN -up-> SB : "Build"
IDE -up-> MVN : "Uses"
GIT -up-> IDE : "Version\nControl"

note right of SB
  **Framework chính**
  - Auto-configuration
  - Embedded Tomcat
  - Production-ready
end note

note bottom of DB
  **Database**
  - Character Set: UTF8MB4
  - Engine: InnoDB
  - 6 main tables
end note

@enduml
```

---

## CHƯƠNG 3: PHÂN TÍCH, THIẾT KẾ HỆ THỐNG

### Hình 3.1. Use Case Diagram Tổng quát

```plantuml
@startuml
left to right direction
skinparam packageStyle rectangle

actor "User" as U
actor "Admin" as A
actor "System\nScheduler" as S

rectangle "Recipe Discovery System" {
  
  package "Authentication" {
    usecase "UC1: Đăng ký" as UC1
    usecase "UC2: Đăng nhập" as UC2
    usecase "UC3: Đăng xuất" as UC3
  }
  
  package "Recipe Management" {
    usecase "UC4: Tạo công thức" as UC4
    usecase "UC5: Sửa công thức" as UC5
    usecase "UC6: Xóa công thức" as UC6
    usecase "UC7: Xem danh sách" as UC7
    usecase "UC8: Xem chi tiết" as UC8
  }
  
  package "Meal Planning" {
    usecase "UC9: Tạo meal plan" as UC9
    usecase "UC10: Thêm món ăn" as UC10
    usecase "UC11: Xóa món ăn" as UC11
    usecase "UC12: Xem meal plan" as UC12
  }
  
  package "Email Notification" {
    usecase "UC13: Gửi email\ntự động" as UC13
  }
  
  package "Community" {
    usecase "UC14: Chia sẻ recipe" as UC14
    usecase "UC15: Xem cộng đồng" as UC15
    usecase "UC16: Lưu favorite" as UC16
  }
  
  package "Admin Functions" {
    usecase "UC17: Quản lý users" as UC17
    usecase "UC18: Kiểm duyệt" as UC18
    usecase "UC19: Xem dashboard" as UC19
  }
  
  package "Categories" {
    usecase "UC20: Quản lý\ncategories" as UC20
  }
}

' User relationships
U --> UC1
U --> UC2
U --> UC3
U --> UC4
U --> UC5
U --> UC6
U --> UC7
U --> UC8
U --> UC9
U --> UC10
U --> UC11
U --> UC12
U --> UC14
U --> UC15
U --> UC16
U --> UC20

' Admin relationships
A --> UC17
A --> UC18
A --> UC19
A --|> U

' System relationships
S --> UC13

' Includes
UC4 ..> UC20 : <<include>>
UC5 ..> UC20 : <<include>>

note right of UC13
  Tự động chạy 4 lần/ngày:
  - 7:00 AM (Breakfast)
  - 11:00 AM (Lunch)
  - 15:00 PM (Snack)
  - 17:00 PM (Dinner)
end note

@enduml
```

### Hình 3.2. Use Case UML của chức năng Đăng ký / Đăng nhập

```plantuml
@startuml
left to right direction

actor User

rectangle "Authentication System" {
  usecase "Đăng ký tài khoản" as UC1
  usecase "Đăng nhập" as UC2
  usecase "Đăng xuất" as UC3
  usecase "Validate email" as V1
  usecase "Validate password" as V2
  usecase "Check credentials" as V3
  usecase "Create session" as V4
  usecase "Destroy session" as V5
}

User --> UC1
User --> UC2
User --> UC3

UC1 ..> V1 : <<include>>
UC1 ..> V2 : <<include>>
UC2 ..> V3 : <<include>>
UC2 ..> V4 : <<include>>
UC3 ..> V5 : <<include>>

note bottom of UC1
  **Đăng ký:**
  - Input: Full name, Email, Password
  - Validate: Email unique, Password >= 6 chars
  - Output: Create new user với role = "USER"
  - Redirect: Login page
end note

note bottom of UC2
  **Đăng nhập:**
  - Input: Email, Password
  - Validate: Credentials match
  - Output: Session created
  - Redirect: /app/home (USER) hoặc
              /admin/dashboard (ADMIN)
end note

@enduml
```

### Hình 3.3. Activity Diagram của chức năng Đăng nhập

```plantuml
@startuml
|User|
start

:Truy cập /login;

|System|
:Hiển thị login form;

|User|
:Nhập email và password;

:Click "Đăng nhập";

|System|
:AuthController nhận POST request;

:AuthService.authenticate(email, password);

:UserRepository.findByEmail(email);

|Database|
:Query users table;

|System|
if (User tồn tại?) then (yes)
  :So sánh password;
  
  if (Password đúng?) then (yes)
    :Tạo session;
    :Lưu user object vào session;
    
    if (User role?) then (ADMIN)
      :Redirect to /admin/dashboard;
      |User|
      :Xem admin dashboard;
      stop
    else (USER)
      :Redirect to /app/home;
      |User|
      :Xem trang chủ;
      stop
    endif
    
  else (no)
    :Return error "Sai mật khẩu";
    |User|
    :Hiển thị thông báo lỗi;
    :Quay lại form login;
    stop
  endif
  
else (no)
  :Return error "Email không tồn tại";
  |User|
  :Hiển thị thông báo lỗi;
  :Quay lại form login;
  stop
endif

@enduml
```

### Hình 3.3b. Activity Diagram của chức năng Đăng ký

```plantuml
@startuml
|User|
start

:Truy cập /register;

|System|
:Hiển thị register form;

|User|
:Nhập thông tin:
- Full name
- Email
- Password;

:Click "Đăng ký";

|System|
:AuthController nhận POST request;

:Validate input data;

if (Dữ liệu hợp lệ?) then (yes)
  :AuthService.register();
  
  :UserRepository.findByEmail(email);
  
  |Database|
  :Query users table;
  
  |System|
  if (Email đã tồn tại?) then (yes)
    :Return error "Email đã được sử dụng";
    
    |User|
    :Hiển thị thông báo lỗi;
    :Quay lại form register;
    stop
    
  else (no)
    :Tạo User entity mới;
    note right
      full_name: <input>
      email: <input>
      password: <input>
      role: "USER"
      status: "ACTIVE"
    end note
    
    |Database|
    :INSERT INTO users;
    
    if (Insert thành công?) then (yes)
      |System|
      :Redirect to /login;
      :Show success message
      "Đăng ký thành công!";
      
      |User|
      :Xem thông báo thành công;
      :Chuyển sang trang đăng nhập;
      stop
      
    else (no)
      |System|
      :Return error "Lỗi database";
      
      |User|
      :Hiển thị thông báo lỗi;
      stop
    endif
  endif
  
else (no)
  :Return validation errors;
  note right
    - Email không hợp lệ
    - Password quá ngắn (<6 chars)
    - Full name trống
  end note
  
  |User|
  :Hiển thị lỗi validation;
  :Sửa thông tin;
  stop
endif

@enduml
```

### Hình 3.4. Use Case UML của chức năng Quản lý công thức

```plantuml
@startuml
left to right direction

actor User

rectangle "Recipe Management System" {
  usecase "Tạo công thức mới" as CREATE
  usecase "Xem danh sách\ncông thức" as LIST
  usecase "Xem chi tiết\ncông thức" as VIEW
  usecase "Sửa công thức" as EDIT
  usecase "Xóa công thức" as DELETE
  usecase "Upload ảnh" as UPLOAD
  usecase "Chọn category" as CAT
  usecase "Validate input" as VAL
  usecase "Save to database" as SAVE
  usecase "Search & Filter" as SEARCH
}

User --> CREATE
User --> LIST
User --> VIEW
User --> EDIT
User --> DELETE
User --> SEARCH

CREATE ..> UPLOAD : <<include>>
CREATE ..> CAT : <<include>>
CREATE ..> VAL : <<include>>
CREATE ..> SAVE : <<include>>

EDIT ..> UPLOAD : <<include>>
EDIT ..> CAT : <<include>>
EDIT ..> VAL : <<include>>
EDIT ..> SAVE : <<include>>

DELETE ..> VAL : <<include>>

note right of CREATE
  **Form fields:**
  - Title (required)
  - Description
  - Ingredients (required)
  - Instructions (required)
  - Image (optional, max 5MB)
  - Category
  - Calories, Cooking time, Servings
end note

@enduml
```

### Hình 3.5. Activity Diagram của chức năng Tạo công thức mới

```plantuml
@startuml
start

:User click "Tạo công thức mới";

:System hiển thị form;

:User điền thông tin;
note right
  - Title
  - Description
  - Ingredients
  - Instructions
  - Category
  - Nutrition info
end note

if (User upload ảnh?) then (yes)
  :Upload ảnh;
  if (Ảnh hợp lệ?) then (yes)
    :Save ảnh vào /uploads/;
  else (no)
    :Show error "File không hợp lệ";
    stop
  endif
else (no)
  :Use default image;
endif

:User click "Lưu";

:Validate form data;

if (Data hợp lệ?) then (yes)
  :Create Recipe entity;
  :Save to database;
  
  if (Save thành công?) then (yes)
    :Redirect to recipe detail;
    :Show success message;
    stop
  else (no)
    :Show error "Lỗi lưu database";
    stop
  endif
else (no)
  :Show validation errors;
  :Return to form;
  stop
endif

@enduml
```

### Hình 3.6. Use Case UML của chức năng Meal Planning

```plantuml
@startuml
left to right direction

actor User

rectangle "Meal Planning System" {
  usecase "Xem meal plan\ntuần hiện tại" as VIEW
  usecase "Thêm món vào slot" as ADD
  usecase "Xóa món khỏi slot" as REMOVE
  usecase "Chọn ngày" as DAY
  usecase "Chọn khung giờ" as TIME
  usecase "Select recipe" as SELECT
  usecase "Auto-create plan\nnếu chưa có" as AUTO
}

User --> VIEW
User --> ADD
User --> REMOVE

VIEW ..> AUTO : <<include>>
ADD ..> DAY : <<include>>
ADD ..> TIME : <<include>>
ADD ..> SELECT : <<include>>
REMOVE ..> DAY : <<include>>
REMOVE ..> TIME : <<include>>

note right of VIEW
  **Calendar view:**
  - 7 cột: Mon - Sun
  - 4 hàng: Breakfast, Lunch,
           Snack, Dinner
  - Mỗi ô: Recipe hoặc "+"
end note

note bottom of TIME
  **4 khung giờ:**
  - BREAKFAST (7-9h)
  - LUNCH (11-13h)
  - SNACK (15-17h)
  - DINNER (17-20h)
end note

@enduml
```

### Hình 3.7. Activity Diagram của chức năng Thêm món vào Meal Plan

```plantuml
@startuml
|User|
start

:Truy cập /app/meal-plan;

|System|
:Kiểm tra meal plan tuần hiện tại;

if (Meal plan tồn tại?) then (no)
  :Tạo MealPlan mới\nweek_start_date = Monday;
  |Database|
  :INSERT INTO meal_plans;
endif

|System|
:Hiển thị calendar view\n7 ngày x 4 khung giờ;

|User|
:Click "+" tại slot cụ thể\n(Thứ 2, Sáng);

|System|
:Mở modal hiển thị\ndanh sách recipes của user;

|User|
:Chọn recipe từ list;

:Click "Thêm";

|System|
:MealPlanController nhận request\n{recipeId, day=MONDAY, mealType=BREAKFAST};

:MealPlanService.addMealToSlot();

:Get MealPlan cho tuần hiện tại;

:Create MealPlanItem entity;
note right
  meal_plan_id: <plan_id>
  recipe_id: <selected_recipe_id>
  day_of_week: 1 (Monday)
  meal_type: "BREAKFAST"
end note

|Database|
:INSERT INTO meal_plan_items;

if (Insert thành công?) then (yes)
  |System|
  :Redirect to /app/meal-plan;
  
  :Refresh calendar view;
  
  |User|
  :Món ăn hiển thị trong\nslot Thứ 2, Sáng;
  
  :Xem thông tin món:\n- Tên món\n- Ảnh\n- Calories;
  
  stop
  
else (no)
  |System|
  :Show error "Lỗi khi thêm món";
  
  |User|
  :Xem thông báo lỗi;
  stop
endif

@enduml
```

### Hình 3.8. Use Case UML của chức năng Email Notification

```plantuml
@startuml
left to right direction

actor "System\nScheduler" as S

rectangle "Email Notification System" {
  usecase "Trigger theo\ncron schedule" as CRON
  usecase "Send breakfast\nnotifications" as BREAK
  usecase "Send lunch\nnotifications" as LUNCH
  usecase "Send snack\nnotifications" as SNACK
  usecase "Send dinner\nnotifications" as DINNER
  usecase "Get users với\nmeal plan" as USERS
  usecase "Render email\ntemplate" as RENDER
  usecase "Send via SMTP" as SEND
  usecase "Log results" as LOG
}

S --> CRON

CRON ..> BREAK : <<extend>>\n@7:00 AM
CRON ..> LUNCH : <<extend>>\n@11:00 AM
CRON ..> SNACK : <<extend>>\n@15:00 PM
CRON ..> DINNER : <<extend>>\n@17:00 PM

BREAK ..> USERS : <<include>>
BREAK ..> RENDER : <<include>>
BREAK ..> SEND : <<include>>
BREAK ..> LOG : <<include>>

LUNCH ..> USERS : <<include>>
LUNCH ..> RENDER : <<include>>
LUNCH ..> SEND : <<include>>
LUNCH ..> LOG : <<include>>

note bottom of CRON
  **Cron Expressions:**
  - 0 0 7 * * ?  -> 7:00 AM
  - 0 0 11 * * ? -> 11:00 AM
  - 0 0 15 * * ? -> 15:00 PM
  - 0 0 17 * * ? -> 17:00 PM
end note

note right of SEND
  **SMTP Config:**
  - Host: smtp.gmail.com
  - Port: 587
  - TLS: enabled
  - Username: thaibao9714@gmail.com
  - Password: App Password
end note

@enduml
```

### Hình 3.9. Activity Diagram của Email Scheduler

```plantuml
@startuml
|Scheduler|
start

:Cron job triggers\n(e.g., 7:00 AM);

:Determine meal type\n(BREAKFAST);

|Database|
:Query all users\nwith email;

:Get current day\n(e.g., MONDAY);

:Get week start date;

|Scheduler|
partition "For each user" {
  :Get user's meal plan\nfor current week;
  
  if (Meal plan exists?) then (yes)
    :Find meal_plan_item where\nday=MONDAY AND\nmealType=BREAKFAST;
    
    if (Item exists?) then (yes)
      if (Recipe != null?) then (yes)
        |Email Service|
        :Get recipe details\n(title, ingredients, instructions);
        
        :Prepare email context\n{user, recipe, mealType};
        
        :Render email template\nwith Thymeleaf;
        
        :Send email via\nGmail SMTP;
        
        if (Send success?) then (yes)
          :Log success;
          :sentCount++;
        else (no)
          :Log error;
          :failCount++;
        endif
      else (no)
        :Skip (no recipe);
      endif
    else (no)
      :Skip (no meal planned);
    endif
  else (no)
    :Skip (no meal plan);
  endif
}

|Scheduler|
:Log summary\n(sent: X, failed: Y);

stop

@enduml
```

### Hình 3.10. Use Case UML của chức năng Community Sharing

```plantuml
@startuml
left to right direction

actor User
actor Admin

rectangle "Community Sharing System" {
  usecase "Chia sẻ recipe\n(set public)" as SHARE
  usecase "Unpublish recipe\n(set private)" as UNSHARE
  usecase "Xem public recipes" as BROWSE
  usecase "Search recipes" as SEARCH
  usecase "Lưu favorite" as FAV
  usecase "Xóa favorite" as UNFAV
  usecase "Kiểm duyệt\nnội dung" as MODERATE
}

User --> SHARE
User --> UNSHARE
User --> BROWSE
User --> SEARCH
User --> FAV
User --> UNFAV

Admin --> MODERATE
Admin --|> User

note right of SHARE
  **Share recipe:**
  - Recipe detail page
  - Button "Chia sẻ"
  - Confirm dialog
  - Set is_public = true
  - Recipe appears in community
end note

note bottom of BROWSE
  **Community page:**
  - List all is_public = true recipes
  - Exclude own recipes
  - Pagination (12/page)
  - Filter by category
  - Sort by date/popular
end note

@enduml
```

### Hình 3.11. Activity Diagram của chức năng Chia sẻ công thức

```plantuml
@startuml
start

:User mở recipe detail page;

if (Recipe belongs to user?) then (yes)
  :Show "Chia sẻ" button;
  
  :User click "Chia sẻ";
  
  :Show confirm dialog\n"Bạn muốn chia sẻ công thức\nvới cộng đồng?";
  
  if (User confirms?) then (yes)
    :Update recipe\nset is_public = true;
    
    :Save to database;
    
    if (Save success?) then (yes)
      :Show success message\n"Đã chia sẻ công thức";
      
      :Recipe xuất hiện\ntrong /app/community;
      
      stop
    else (no)
      :Show error\n"Lỗi khi chia sẻ";
      stop
    endif
  else (no)
    :Cancel;
    stop
  endif
  
else (no)
  :Hide "Chia sẻ" button;
  stop
endif

@enduml
```

---

### Hình 3.12. Bảng users trong Cơ sở dữ liệu

```sql
-- Table structure for users
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  avatar_url VARCHAR(500) DEFAULT 'https://ui-avatars.com/api/?name=User&background=4caf50&color=fff',
  note VARCHAR(255) NULL,
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  INDEX idx_email (email),
  INDEX idx_role (role),
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Markdown Table:**

| Field | Type | Constraints | Default | Description |
|-------|------|-------------|---------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | - | ID người dùng |
| full_name | VARCHAR(100) | NOT NULL | - | Họ và tên |
| email | VARCHAR(100) | UNIQUE, NOT NULL | - | Email đăng nhập |
| password | VARCHAR(255) | NOT NULL | - | Mật khẩu |
| role | VARCHAR(20) | NOT NULL | 'USER' | Quyền (USER/ADMIN) |
| avatar_url | VARCHAR(500) | - | Default avatar | URL ảnh đại diện |
| note | VARCHAR(255) | NULL | - | Ghi chú |
| status | VARCHAR(20) | - | 'ACTIVE' | Trạng thái (ACTIVE/BANNED) |
| created_at | TIMESTAMP | NOT NULL | NOW() | Ngày tạo |
| updated_at | TIMESTAMP | NOT NULL | NOW() | Ngày cập nhật |

### Hình 3.13. Bảng recipes trong Cơ sở dữ liệu

```sql
-- Table structure for recipes
CREATE TABLE recipes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  category_id BIGINT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  ingredients TEXT NOT NULL,
  instructions TEXT NOT NULL,
  image_url VARCHAR(500),
  calories INT,
  cooking_time INT COMMENT 'in minutes',
  servings INT,
  is_public BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES user_categories(id) ON DELETE SET NULL,
  
  INDEX idx_user_id (user_id),
  INDEX idx_category_id (category_id),
  INDEX idx_is_public (is_public),
  INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Markdown Table:**

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | ID công thức |
| user_id | BIGINT | FK, NOT NULL | ID người tạo |
| category_id | BIGINT | FK, NULL | ID danh mục |
| title | VARCHAR(255) | NOT NULL | Tên món ăn |
| description | TEXT | - | Mô tả |
| ingredients | TEXT | NOT NULL | Nguyên liệu |
| instructions | TEXT | NOT NULL | Hướng dẫn nấu |
| image_url | VARCHAR(500) | - | Đường dẫn ảnh |
| calories | INT | - | Lượng calories |
| cooking_time | INT | - | Thời gian nấu (phút) |
| servings | INT | - | Số khẩu phần |
| is_public | BOOLEAN | DEFAULT FALSE | Công khai? |
| created_at | TIMESTAMP | NOT NULL | Ngày tạo |
| updated_at | TIMESTAMP | NOT NULL | Ngày cập nhật |

### Hình 3.14-3.17. Các bảng khác

Tương tự, tạo tables cho:
- meal_plans
- meal_plan_items  
- user_categories
- favorites

### Hình 3.18. Sơ đồ ERD Diagram của Recipe Discovery

```plantuml
@startuml
skinparam linetype ortho

entity "users" as users {
  * id : BIGINT <<PK>>
  --
  * full_name : VARCHAR(100)
  * email : VARCHAR(100) <<UK>>
  * password : VARCHAR(255)
  * role : VARCHAR(20)
  avatar_url : VARCHAR(500)
  note : VARCHAR(255)
  status : VARCHAR(20)
  * created_at : TIMESTAMP
  * updated_at : TIMESTAMP
}

entity "recipes" as recipes {
  * id : BIGINT <<PK>>
  --
  * user_id : BIGINT <<FK>>
  category_id : BIGINT <<FK>>
  * title : VARCHAR(255)
  description : TEXT
  * ingredients : TEXT
  * instructions : TEXT
  image_url : VARCHAR(500)
  calories : INT
  cooking_time : INT
  servings : INT
  is_public : BOOLEAN
  * created_at : TIMESTAMP
  * updated_at : TIMESTAMP
}

entity "user_categories" as categories {
  * id : BIGINT <<PK>>
  --
  * user_id : BIGINT <<FK>>
  * name : VARCHAR(100)
  color_code : VARCHAR(20)
  icon : VARCHAR(100)
  * created_at : TIMESTAMP
  * updated_at : TIMESTAMP
}

entity "meal_plans" as plans {
  * id : BIGINT <<PK>>
  --
  * user_id : BIGINT <<FK>>
  * week_start_date : DATE
  * created_at : TIMESTAMP
}

entity "meal_plan_items" as items {
  * id : BIGINT <<PK>>
  --
  * meal_plan_id : BIGINT <<FK>>
  recipe_id : BIGINT <<FK>>
  * day_of_week : INT
  * meal_type : VARCHAR(20)
  * created_at : TIMESTAMP
}

entity "favorites" as favorites {
  * id : BIGINT <<PK>>
  --
  * user_id : BIGINT <<FK>>
  * recipe_id : BIGINT <<FK>>
  * created_at : TIMESTAMP
}

' Relationships
users ||--o{ recipes : "creates"
users ||--o{ categories : "has"
users ||--o{ plans : "plans"
users ||--o{ favorites : "saves"

categories ||--o{ recipes : "categorizes"

recipes ||--o{ items : "used in"
recipes ||--o{ favorites : "favorited by"

plans ||--|{ items : "contains"

@enduml
```

---

## CHƯƠNG 4: GIAO DIỆN

> **Lưu ý:** Chương 4 cần screenshots thực tế từ ứng dụng.
> Hãy chạy app, chụp màn hình các trang sau và đưa vào báo cáo:

### Danh sách Screenshots cần chụp:

1. **Hình 4.1. Giao diện đăng nhập**
   - URL: http://localhost:8080/login
   - Chụp full page

2. **Hình 4.2. Giao diện đăng ký**
   - URL: http://localhost:8080/register
   - Chụp full page

3. **Hình 4.3. Giao diện Công thức cá nhân**
   - URL: http://localhost:8080/app/home
   - Chụp danh sách recipes

4. **Hình 4.4. Giao diện Tạo/Chỉnh sửa công thức**
   - URL: http://localhost:8080/app/recipes/create
   - Chụp form

5. **Hình 4.5. Giao diện Chi tiết công thức**
   - URL: http://localhost:8080/app/recipes/{id}
   - Chụp recipe detail

6. **Hình 4.6. Giao diện Kế hoạch bữa ăn**
   - URL: http://localhost:8080/app/meal-plan
   - Chụp calendar view

7. **Hình 4.7. Giao diện Cộng đồng**
   - URL: http://localhost:8080/app/community
   - Chụp public recipes

8. **Hình 4.8. Giao diện Admin Dashboard**
   - URL: http://localhost:8080/admin/dashboard
   - Chụp admin page

9. **Hình 4.9. Email thông báo**
   - Check inbox
   - Chụp email nhận được

---

## Hướng dẫn sử dụng

### Cách tạo diagram từ PlantUML:

**Option 1: Online (Nhanh nhất)**
1. Vào https://www.plantuml.com/plantuml/uml/
2. Copy mã PlantUML
3. Paste vào editor
4. Download PNG hoặc SVG

**Option 2: VS Code Plugin**
1. Install extension "PlantUML"
2. Install Graphviz
3. Paste mã vào file .puml
4. Preview với Alt+D
5. Export PNG

**Option 3: IntelliJ IDEA Plugin**
1. Install "PlantUML integration"
2. Tạo file .puml
3. Paste code
4. Right-click → Export Diagram

### Tips cho báo cáo:

1. **Diagram size:** Export với độ phân giải cao (300 DPI)
2. **Naming:** Đặt tên file theo số hình (hinh_3_1.png)
3. **Placement:** Đưa hình vào đúng vị trí trong chương
4. **Caption:** Thêm caption dưới mỗi hình
5. **Reference:** Tham chiếu đến hình trong text: "như Hình 3.1 minh họa..."

---

**File này chứa tất cả UML diagrams cần thiết cho báo cáo!**
