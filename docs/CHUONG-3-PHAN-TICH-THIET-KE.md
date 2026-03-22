# CHƯƠNG III: PHÂN TÍCH, THIẾT KẾ HỆ THỐNG

## 3.1. Mô tả bài toán

Trong thực tế, người yêu thích nấu ăn thường gặp khó khăn trong việc quản lý công thức nấu ăn cá nhân do công thức được lưu trữ phân tán trên nhiều nơi (sổ tay, ảnh, app khác nhau), khó tìm kiếm khi cần và không có khả năng lập kế hoạch bữa ăn hợp lý. Việc ghi chép thủ công hoặc chỉ nhớ trong đầu dễ dẫn đến thất lạc thông tin, quên nguyên liệu hoặc không có cách tổ chức khoa học.

Bài toán đặt ra là xây dựng một hệ thống web application quản lý công thức nấu ăn cho phép người dùng:
- Lưu trữ công thức nấu ăn với đầy đủ thông tin: tên món, nguyên liệu, hướng dẫn, ảnh, dinh dưỡng.
- Lập kế hoạch bữa ăn (meal planning) theo tuần một cách trực quan với 4 khung giờ mỗi ngày.
- Nhận thông báo email tự động về bữa ăn đã lên lịch đúng thời điểm.
- Chia sẻ công thức với cộng đồng và khám phá món ăn mới từ người khác.

Hệ thống cần đảm bảo tính đơn giản, dễ sử dụng, responsive trên mọi thiết bị và phù hợp với nhu cầu quản lý ẩm thực hằng ngày của người dùng.

## 3.2. Sơ đồ Use Case

### 3.2.1. Sơ đồ Use Case tổng quát

**Hình 3.1. Use Case Diagram Tổng quát**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

Hệ thống Recipe Discovery có 3 tác nhân chính:
1. **User (Người dùng thông thường):** Sử dụng các chức năng quản lý công thức, meal planning, community
2. **Admin (Quản trị viên):** Kế thừa User + có quyền quản lý users và kiểm duyệt nội dung
3. **System Scheduler (Hệ thống tự động):** Gửi email notifications theo lịch

Các nhóm chức năng chính của hệ thống bao gồm:

**Authentication (Xác thực):**
- UC1: Đăng ký tài khoản
- UC2: Đăng nhập hệ thống  
- UC3: Đăng xuất

**Recipe Management (Quản lý công thức):**
- UC4: Tạo công thức mới
- UC5: Sửa công thức
- UC6: Xóa công thức
- UC7: Xem danh sách công thức
- UC8: Xem chi tiết công thức

**Meal Planning (Kế hoạch bữa ăn):**
- UC9: Tạo meal plan tuần
- UC10: Thêm món vào slot
- UC11: Xóa món khỏi slot
- UC12: Xem meal plan

**Email Notification (Thông báo email):**
- UC13: Gửi email tự động (4 lần/ngày)

**Community (Cộng đồng):**
- UC14: Chia sẻ recipe công khai
- UC15: Xem recipes cộng đồng
- UC16: Lưu recipe yêu thích

**Admin Functions (Quản trị):**
- UC17: Quản lý users
- UC18: Kiểm duyệt nội dung
- UC19: Xem dashboard thống kê

**Categories (Danh mục):**
- UC20: Quản lý categories (tạo, sửa, xóa)

### 3.2.2. Đặc tả Use Case

#### 3.2.2.1. Chức năng Quản lý người dùng & Xác thực

**a. Mục tiêu**
- Cung cấp khả năng đăng ký tài khoản mới cho người dùng lần đầu sử dụng hệ thống.
- Cho phép đăng nhập vào hệ thống bằng email và mật khẩu hợp lệ.
- Xác thực người dùng để truy cập các tính năng khác (recipes, meal plan, community).
- Phân quyền USER và ADMIN để kiểm soát truy cập.

**b. Phân tích nghiệp vụ**

*Use Case*
- **Actor:** User (người dùng mới hoặc đã có tài khoản)
- **Các Use Case chính:**
  - **Đăng ký tài khoản:** Nhập thông tin (full name, email, password), kiểm tra email chưa tồn tại, mã hóa password, lưu vào database với role = "USER".
  - **Đăng nhập hệ thống:** Xác thực email/password, tạo session, redirect theo role (USER → /app/home, ADMIN → /admin/dashboard).
  - **Đăng xuất:** Destroy session, redirect về /login.
  - **Xác thực thông tin** (include) → được gọi khi đăng ký (check email unique) hoặc đăng nhập (verify credentials).
  - **Tạo session** (include) → được gọi sau khi đăng nhập thành công.
  - **Thông báo kết quả** → hiển thị success/error messages.

**Hình 3.2. Use Case UML của chức năng Đăng ký / Đăng nhập**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

*Luồng hoạt động*

**Luồng chính (Đăng nhập):**
1. User truy cập `/login`
2. Hệ thống hiển thị login form
3. User nhập email, password
4. User click "Đăng nhập"
5. AuthController nhận POST request
6. AuthService.authenticate(email, password)
7. UserRepository.findByEmail(email)
8. **Alt:** Nếu user tồn tại:
   - So sánh password (plaintext - cần cải thiện với BCrypt)
   - **Alt:** Nếu password đúng:
     - Tạo session, lưu user object
     - Redirect theo role
   - **Alt:** Nếu password sai:
     - Show error "Sai mật khẩu"
9. **Alt:** Nếu user không tồn tại:
   - Show error "Email không tồn tại"

**Luồng chính (Đăng ký):**
1. User truy cập `/register`
2. Hệ thống hiển thị register form
3. User nhập full name, email, password
4. User click "Đăng ký"
5. AuthController validate input
6. Check email đã tồn tại chưa
7. **Alt:** Nếu email chưa tồn tại:
   - Tạo User entity với role = "USER"
   - Save vào database
   - Redirect về `/login` với success message
8. **Alt:** Nếu email đã tồn tại:
   - Show error "Email đã được sử dụng"

**Hình 3.3. Activity Diagram của chức năng Đăng nhập**

*(Xem chi tiết Activity Diagram trong file UML-DIAGRAMS.md)*

**Hình 3.3b. Activity Diagram của chức năng Đăng ký**

*(Xem chi tiết Activity Diagram trong file UML-DIAGRAMS.md)*

#### 3.2.2.2. Chức năng Quản lý công thức cá nhân

**a. Mục tiêu**
- Giúp người dùng tạo và quản lý danh sách các công thức nấu ăn cá nhân.
- Cho phép người dùng thêm, sửa, xóa và xem thông tin chi tiết từng công thức.
- Hỗ trợ upload ảnh món ăn, phân loại theo categories.
- Là nguồn dữ liệu chính cho các chức năng Meal Planning và Community Sharing.

**b. Phân tích nghiệp vụ**

*Use Case*
- **Actor:** User (đã đăng nhập)
- **Các Use Case chính:**
  - **Xem danh sách công thức:** Hiển thị tất cả recipes của user hiện tại, hỗ trợ pagination (12 items/page), search by title, filter by category.
  - **Xem chi tiết công thức:** Hiển thị đầy đủ thông tin: title, description, ingredients, instructions, image, calories, cooking time, servings, category.
  - **Tạo công thức mới:** Nhập thông tin qua form, upload ảnh (optional, max 5MB, JPEG/PNG), chọn category, validate input, save to database.
  - **Sửa công thức:** Load data hiện tại vào form, cho phép chỉnh sửa, upload ảnh mới (giữ ảnh cũ nếu không upload), update database.
  - **Xóa công thức:** Confirm dialog, hard delete khỏi database, cascade delete meal_plan_items references.
  - **Upload ảnh** (include) → được gọi khi tạo hoặc sửa recipe.
  - **Chọn category** (include) → dropdown list categories của user.
  - **Validate input** (include) → kiểm tra required fields.
  - **Save to database** (include) → lưu entity vào recipes table.
  - **Search & Filter:** Tìm kiếm theo keyword, lọc theo category, sort by date.

**Hình 3.4. Use Case UML của chức năng Quản lý công thức**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

*Luồng hoạt động*

**Luồng chính (Tạo công thức):**
1. User click "Tạo công thức mới" tại `/app/recipes/create`
2. Hệ thống hiển thị form với các fields:
   - Title (required)
   - Description (optional)
   - Ingredients (required, textarea)
   - Instructions (required, textarea)
   - Image (optional, file upload)
   - Category (dropdown)
   - Calories, Cooking time, Servings (numbers)
3. User điền thông tin
4. **Alt:** Nếu user upload ảnh:
   - Validate file type (JPEG/PNG), size (max 5MB)
   - **Alt:** Nếu hợp lệ: Save vào `/uploads/` folder
   - **Alt:** Nếu không hợp lệ: Show error
5. User click "Lưu"
6. Controller validate form data
7. **Alt:** Nếu valid:
   - RecipeService.create(RecipeDTO)
   - Map DTO to Recipe entity
   - Set user_id = current user
   - RecipeRepository.save(recipe)
   - Redirect to `/app/recipes/{id}` với success message
8. **Alt:** Nếu invalid:
   - Show validation errors
   - Return to form với data preserved

**Hình 3.5. Activity Diagram của chức năng Tạo công thức mới**

*(Xem chi tiết Activity Diagram trong file UML-DIAGRAMS.md)*

#### 3.2.2.3. Chức năng Kế hoạch bữa ăn (Meal Planning)

**a. Mục tiêu**
- Cho phép người dùng lập kế hoạch bữa ăn cho cả tuần (7 ngày).
- Tổ chức theo 4 khung giờ mỗi ngày: Breakfast (7-9h), Lunch (11-13h), Snack (15-17h), Dinner (17-20h).
- Tự động tạo meal plan cho tuần hiện tại nếu chưa có.
- Hiển thị meal plan dưới dạng calendar view trực quan.
- Đồng bộ dữ liệu với Email Notification system.

**b. Phân tích nghiệp vụ**

*Use Case*
- **Actor:** User (đã đăng nhập, đã có recipes)
- **Các Use Case chính:**
  - **Xem meal plan tuần hiện tại:** Hiển thị calendar 7 cột (Mon-Sun) x 4 hàng (Breakfast, Lunch, Snack, Dinner). Mỗi cell hiển thị recipe info hoặc "+" button để add.
  - **Auto-create meal plan nếu chưa có** (include): Khi user truy cập `/app/meal-plan` lần đầu trong tuần, hệ thống tự động tạo MealPlan(user_id, week_start_date = Monday).
  - **Thêm món vào slot:** User click "+", mở modal với list recipes của user, chọn recipe, insert vào meal_plan_items với (meal_plan_id, recipe_id, day_of_week, meal_type).
  - **Xóa món khỏi slot:** User click "X" trên recipe card, confirm, delete từ meal_plan_items.
  - **Chọn ngày** (include): Xác định day_of_week (1=Monday...7=Sunday).
  - **Chọn khung giờ** (include): Xác định meal_type (BREAKFAST/LUNCH/SNACK/DINNER).
  - **Select recipe** (include): Modal list recipes để chọn.

**Hình 3.6. Use Case UML của chức năng Meal Planning**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

*Luồng hoạt động*

**Luồng chính (Thêm món vào Meal Plan):**
1. User truy cập `/app/meal-plan`
2. Hệ thống kiểm tra MealPlan cho tuần hiện tại
3. **Alt:** Nếu chưa có:
   - Tạo MealPlan mới với week_start_date = Monday tuần này
4. Hiển thị calendar view
5. User click "+" tại slot cụ thể (ví dụ: Thứ 2, Sáng)
6. Mở modal hiển thị list recipes của user
7. User chọn recipe từ list
8. Submit form POST `/app/meal-plan/add` với params:
   - recipeId
   - day (MONDAY)
   - mealType (BREAKFAST)
9. MealPlanService.addMealToSlot():
   - Get or create MealPlan
   - Create MealPlanItem(meal_plan_id, recipe_id, day=1, meal_type="BREAKFAST")
   - Save to database
10. Redirect về `/app/meal-plan`
11. Recipe hiển thị trong slot Thứ 2, Sáng

**Hình 3.7. Activity Diagram của chức năng Thêm món vào Meal Plan**

*(Xem chi tiết Activity Diagram trong file UML-DIAGRAMS.md)*

#### 3.2.2.4. Chức năng Email Notification

**a. Mục tiêu**
- Nhắc nhở người dùng về bữa ăn đã lên lịch trong meal plan đúng thời điểm.
- Gửi email tự động 4 lần mỗi ngày: 7:00 AM, 11:00 AM, 15:00 PM, 17:00 PM.
- Hoạt động ngay cả khi user không online (server-side scheduled tasks).
- Email chứa đầy đủ thông tin món ăn: tên, nguyên liệu, hướng dẫn, dinh dưỡng.

**b. Phân tích nghiệp vụ**

*Use Case*
- **Actor:** System Scheduler (tự động), User (nhận email)
- **Các Use Case chính:**
  
  **System Scheduler:**
  - **Trigger theo cron schedule:** 4 scheduled methods với @Scheduled annotation:
    - sendBreakfastNotifications() @ 7:00 AM
    - sendLunchNotifications() @ 11:00 AM
    - sendSnackNotifications() @ 15:00 PM
    - sendDinnerNotifications() @ 17:00 PM
  - **Get users với meal plan** (include): Query tất cả users có email, lọc users có meal plan tuần hiện tại.
  - **Render email template** (include): Sử dụng Thymeleaf render `meal-notification-email.html` với context {user, recipe, mealType}.
  - **Send via SMTP** (include): JavaMailSender gửi email qua Gmail SMTP (smtp.gmail.com:587).
  - **Log results** (include): Ghi log số email sent/failed.

  **User:**
  - **Nhận email notification:** Email đến inbox với subject "🍳 Bữa sáng hôm nay", nội dung chứa recipe details.

**Hình 3.8. Use Case UML của chức năng Email Notification**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

*Luồng hoạt động*

**Luồng chính (Send Breakfast Notifications):**
1. **7:00 AM:** Cron job trigger sendBreakfastNotifications()
2. Xác định meal type = BREAKFAST
3. Get current day (ví dụ: MONDAY, day_of_week = 1)
4. Get week start date (Monday current week)
5. Query all users có email
6. **For each user:**
   - Get meal plan cho tuần hiện tại
   - **Alt:** Nếu meal plan tồn tại:
     - Find meal_plan_item WHERE day_of_week=1 AND meal_type="BREAKFAST"
     - **Alt:** Nếu item tồn tại:
       - **Alt:** Nếu recipe != null:
         - Force load recipe data (title, ingredients, instructions, calories...)
         - Prepare email context
         - Render template với Thymeleaf
         - Send email via Gmail SMTP
         - **Alt:** Nếu gửi thành công: Log success, sentCount++
         - **Alt:** Nếu gửi thất bại: Log error, failCount++
7. Log summary: "✅ Completed BREAKFAST notifications. Sent X emails."

**Hình 3.9. Activity Diagram của Email Scheduler**

*(Xem chi tiết Activity Diagram với swimlanes trong file UML-DIAGRAMS.md)*

#### 3.2.2.5. Chức năng Community Sharing

**a. Mục tiêu**
- Cho phép người dùng chia sẻ công thức của mình với cộng đồng.
- Xem công thức công khai từ người dùng khác để học hỏi món ăn mới.
- Lưu các công thức yêu thích vào favorites.
- Admin có thể kiểm duyệt và unpublish recipes vi phạm.

**b. Phân tích nghiệp vụ**

*Use Case*
- **Actor:** User, Admin
- **Các Use Case chính:**
  
  **User:**
  - **Chia sẻ recipe (set public):** Tại recipe detail page, click "Chia sẻ", confirm dialog, set is_public = true, recipe xuất hiện trong /app/community.
  - **Unpublish recipe (set private):** Click "Hủy chia sẻ", set is_public = false, recipe biến mất khỏi community.
  - **Xem public recipes:** List tất cả recipes có is_public = true (trừ recipes của chính mình), pagination 12/page, search và filter.
  - **Lưu favorite:** Click "♥" trên recipe, insert vào favorites table (user_id, recipe_id).
  - **Xóa favorite:** Click "♥" màu đỏ, delete từ favorites.
  
  **Admin:**
  - **Kiểm duyệt nội dung:** Xem tất cả public recipes, có quyền unpublish (set is_public = false) nếu vi phạm.

**Hình 3.10. Use Case UML của chức năng Community Sharing**

*(Xem chi tiết PlantUML code trong file UML-DIAGRAMS.md)*

*Luồng hoạt động*

**Luồng chính (Chia sẻ công thức):**
1. User mở recipe detail page của công thức do mình tạo
2. **Alt:** Nếu recipe.user_id == current_user.id:
   - Hiển thị button "Chia sẻ với cộng đồng"
3. User click "Chia sẻ"
4. Show confirm dialog: "Bạn muốn chia sẻ công thức này với mọi người?"
5. **Alt:** Nếu user confirms:
   - RecipeService.shareRecipe(recipeId)
   - Update recipe SET is_public = true
   - Save to database
   - **Alt:** Nếu save thành công:
     - Redirect về recipe detail với message "Đã chia sẻ công thức"
     - Recipe xuất hiện trong /app/community
   - **Alt:** Nếu save thất bại:
     - Show error "Lỗi khi chia sẻ"
6. **Alt:** Nếu user cancels:
   - Đóng dialog, không làm gì

**Hình 3.11. Activity Diagram của chức năng Chia sẻ công thức**

*(Xem chi tiết Activity Diagram trong file UML-DIAGRAMS.md)*

## 3.3. Thiết kế cơ sở dữ liệu

### 3.3.1. Các bảng chính trong hệ thống

#### 3.3.1.1. Bảng users

**Chức năng:** Lưu thông tin người dùng và xác thực.

**Ý nghĩa:** Bảng users là nền tảng xác định danh tính và phân quyền của người dùng. Mỗi người đăng nhập vào hệ thống đều có một dòng dữ liệu duy nhất trong bảng này. Hỗ trợ 2 roles: USER (người dùng thông thường) và ADMIN (quản trị viên).

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh duy nhất cho mỗi người dùng.
- `full_name` (VARCHAR(100), NOT NULL): Họ và tên người dùng, hiển thị trong profile và giao diện.
- `email` (VARCHAR(100), NOT NULL, UNIQUE): Địa chỉ email dùng để đăng nhập, phải unique.
- `password` (VARCHAR(255), NOT NULL): Mật khẩu của người dùng (⚠️ hiện tại plaintext, cần mã hóa BCrypt).
- `role` (VARCHAR(20), DEFAULT 'USER'): Phân quyền - "USER" hoặc "ADMIN".
- `avatar_url` (VARCHAR(500)): URL ảnh đại diện (mặc định: UI Avatars).
- `note` (VARCHAR(255)): Ghi chú của admin về user này.
- `status` (VARCHAR(20), DEFAULT 'ACTIVE'): Trạng thái tài khoản - "ACTIVE" hoặc "BANNED".
- `created_at` (TIMESTAMP, DEFAULT NOW()): Thời điểm tài khoản được tạo.
- `updated_at` (TIMESTAMP, ON UPDATE NOW()): Thời điểm cập nhật cuối cùng.

**Indexes:**
- `idx_email (email)`: Tăng tốc login queries
- `idx_role (role)`: Tăng tốc phân quyền queries
- `idx_status (status)`: Lọc users theo status

**Hình 3.12. Bảng users trong Cơ sở dữ liệu recipe_discovery**

```sql
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

#### 3.3.1.2. Bảng recipes

**Chức năng:** Quản lý thông tin các công thức nấu ăn.

**Ý nghĩa:** Bảng recipes lưu danh sách tất cả công thức của người dùng. Một user có thể tạo nhiều recipes, mỗi recipe chứa đầy đủ thông tin để nấu món ăn. Recipe có thể là private (chỉ mình user xem) hoặc public (chia sẻ cộng đồng).

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh của công thức.
- `user_id` (FK → users.id, NOT NULL): Người tạo công thức, ON DELETE CASCADE.
- `category_id` (FK → user_categories.id): Danh mục công thức, ON DELETE SET NULL.
- `title` (VARCHAR(255), NOT NULL): Tên món ăn.
- `description` (TEXT): Mô tả ngắn về món ăn.
- `ingredients` (TEXT, NOT NULL): Danh sách nguyên liệu (mỗi dòng 1 nguyên liệu).
- `instructions` (TEXT, NOT NULL): Hướng dẫn nấu từng bước.
- `image_url` (VARCHAR(500)): Đường dẫn ảnh món ăn (lưu trong `/uploads/`).
- `calories` (INT): Lượng calories ước tính.
- `cooking_time` (INT): Thời gian nấu (phút).
- `servings` (INT): Số khẩu phần.
- `is_public` (BOOLEAN, DEFAULT FALSE): Công khai hay không.
- `created_at` (TIMESTAMP): Ngày tạo.
- `updated_at` (TIMESTAMP): Ngày cập nhật.

**Indexes:**
- `idx_user_id (user_id)`: Lấy recipes của 1 user
- `idx_category_id (category_id)`: Lọc theo category
- `idx_is_public (is_public)`: Lấy public recipes
- `idx_created_at (created_at)`: Sort by date

**Hình 3.13. Bảng recipes trong Cơ sở dữ liệu recipe_discovery**

```sql
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

#### 3.3.1.3. Bảng meal_plans

**Chức năng:** Lưu thông tin các kế hoạch bữa ăn theo tuần.

**Ý nghĩa:** Bảng meal_plans lưu kế hoạch bữa ăn của từng người dùng cho mỗi tuần. Một user có một meal plan cho một tuần cụ thể (xác định bởi week_start_date). Hệ thống tự động tạo meal plan mới khi user truy cập meal planning page lần đầu trong tuần.

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh meal plan.
- `user_id` (FK → users.id, NOT NULL): Người sở hữu meal plan, ON DELETE CASCADE.
- `week_start_date` (DATE, NOT NULL): Ngày bắt đầu tuần (Monday).
- `created_at` (TIMESTAMP): Ngày tạo meal plan.

**Unique Constraint:** `UNIQUE(user_id, week_start_date)` - một user chỉ có 1 plan cho 1 tuần.

**Hình 3.14. Bảng meal_plans trong Cơ sở dữ liệu recipe_discovery**

```sql
CREATE TABLE meal_plans (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  week_start_date DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  UNIQUE KEY uk_user_week (user_id, week_start_date),
  INDEX idx_week_start (week_start_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 3.3.1.4. Bảng meal_plan_items

**Chức năng:** Quản lý chi tiết từng món ăn trong meal plan.

**Ý nghĩa:** Bảng meal_plan_items lưu từng slot trong meal plan (ngày + khung giờ + recipe). Mỗi record đại diện cho 1 bữa ăn cụ thể (ví dụ: Thứ 2, Sáng, Phở bò). Bảng này kết nối meal_plans với recipes.

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh item.
- `meal_plan_id` (FK → meal_plans.id, NOT NULL): Meal plan chứa item này, ON DELETE CASCADE.
- `recipe_id` (FK → recipes.id): Recipe được gán vào slot, ON DELETE SET NULL (giữ slot nhưng clear recipe).
- `day_of_week` (INT, NOT NULL): Ngày trong tuần (1=Monday, 2=Tuesday,..., 7=Sunday).
- `meal_type` (VARCHAR(20), NOT NULL): Loại bữa ăn - "BREAKFAST", "LUNCH", "SNACK", "DINNER".
- `created_at` (TIMESTAMP): Ngày thêm vào meal plan.

**Unique Constraint:** `UNIQUE(meal_plan_id, day_of_week, meal_type)` - một slot chỉ có 1 recipe.

**Hình 3.15. Bảng meal_plan_items trong Cơ sở dữ liệu recipe_discovery**

```sql
CREATE TABLE meal_plan_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  meal_plan_id BIGINT NOT NULL,
  recipe_id BIGINT,
  day_of_week INT NOT NULL,
  meal_type VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (meal_plan_id) REFERENCES meal_plans(id) ON DELETE CASCADE,
  FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE SET NULL,
  
  UNIQUE KEY uk_slot (meal_plan_id, day_of_week, meal_type),
  INDEX idx_recipe (recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
``` 

#### 3.3.1.5. Bảng categories

**Chức năng:** Lưu các danh mục công thức chung của hệ thống.

**Ý nghĩa:** Bảng categories lưu danh sách các loại món ăn chung cho toàn hệ thống (ví dụ: Món chính, Món tráng miệng, Đồ uống...). Đây là bảng master data, được tạo sẵn bởi admin. Các recipes có thể được phân loại theo categories này.

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh category.
- `name` (VARCHAR(50), NOT NULL, UNIQUE): Tên danh mục, không trùng lặp.
- `color_code` (VARCHAR(50), DEFAULT '#4caf50'): Mã màu hiển thị.
- `icon` (VARCHAR(50), DEFAULT 'utensils'): Icon hiển thị (FontAwesome icon name).
- `created_at` (TIMESTAMP): Ngày tạo.

**Hình 3.16. Bảng categories trong Cơ sở dữ liệu recipe_discovery**

```sql
CREATE TABLE categories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  color_code VARCHAR(50) NOT NULL DEFAULT '#4caf50',
  icon VARCHAR(50) DEFAULT 'utensils',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 3.3.1.6. Bảng user_categories

**Chức năng:** Lưu các danh mục tùy chỉnh của người dùng.

**Ý nghĩa:** Bảng user_categories cho phép mỗi user tạo các categories riêng để phân loại recipes (ví dụ: Món Việt, Món Âu, Món chay, Bánh ngọt...). Mỗi user có thể có nhiều categories khác nhau.

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh category.
- `user_id` (FK → users.id, NOT NULL): Người tạo category, ON DELETE CASCADE.
- `name` (VARCHAR(100), NOT NULL): Tên danh mục.
- `color_code` (VARCHAR(20)): Mã màu hiển thị (hex code).
- `icon` (VARCHAR(100)): Icon hiển thị (Bootstrap icon name).
- `created_at` (TIMESTAMP): Ngày tạo.
- `updated_at` (TIMESTAMP): Ngày cập nhật.

**Hình 3.17. Bảng user_categories trong Cơ sở dữ liệu recipe_discovery**

```sql
CREATE TABLE user_categories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  color_code VARCHAR(20),
  icon VARCHAR(100),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 3.3.1.7. Bảng favorites

**Chức năng:** Quản lý danh sách công thức yêu thích.

**Ý nghĩa:** Bảng favorites lưu mối quan hệ Many-to-Many giữa users và recipes. Một user có thể save nhiều recipes yêu thích, một recipe có thể được nhiều users favorite. Chỉ áp dụng cho public recipes.

**Các trường dữ liệu chính:**
- `id` (PK, BIGINT, AUTO_INCREMENT): Mã định danh.
- `user_id` (FK → users.id, NOT NULL): Người lưu favorite, ON DELETE CASCADE.
- `recipe_id` (FK → recipes.id, NOT NULL): Recipe được favorite, ON DELETE CASCADE.
- `created_at` (TIMESTAMP): Ngày lưu favorite.

**Unique Constraint:** `UNIQUE(user_id, recipe_id)` - một user chỉ favorite một recipe 1 lần.

**Hình 3.18. Bảng favorites trong Cơ sở dữ liệu recipe_discovery**

```sql
CREATE TABLE favorites (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  recipe_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
  
  UNIQUE KEY uk_user_recipe (user_id, recipe_id),
  INDEX idx_recipe (recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 3.3.2. Mối quan hệ giữa các bảng

**Mối quan hệ chính:**

1. **users (1) ← (N) recipes:**
   - Một user có thể tạo nhiều recipes
   - Mỗi recipe thuộc về 1 user duy nhất
   - Khi user bị xóa → cascade delete tất cả recipes của user

2. **users (1) ← (N) user_categories:**
   - Một user có thể tạo nhiều categories
   - Mỗi category thuộc về 1 user
   - Khi user bị xóa → cascade delete categories

3. **user_categories (1) ← (N) recipes:**
   - Một category có thể chứa nhiều recipes
   - Mỗi recipe thuộc về 0 hoặc 1 category
   - Khi category bị xóa → set recipe.category_id = NULL

4. **users (1) ← (N) meal_plans:**
   - Một user có nhiều meal plans (mỗi tuần 1 plan)
   - Mỗi meal plan thuộc về 1 user
   - Khi user bị xóa → cascade delete meal plans

5. **meal_plans (1) ← (N) meal_plan_items:**
   - Một meal plan chứa nhiều items (tối đa 28 items: 7 ngày x 4 bữa)
   - Mỗi item thuộc về 1 meal plan
   - Khi meal plan bị xóa → cascade delete items

6. **recipes (1) ← (N) meal_plan_items:**
   - Một recipe có thể được dùng trong nhiều meal plan items
   - Mỗi item tham chiếu đến 0 hoặc 1 recipe
   - Khi recipe bị xóa → set item.recipe_id = NULL (giữ slot)

7. **users (N) ← → (N) recipes (qua favorites):**
   - Một user có thể favorite nhiều recipes
   - Một recipe có thể được nhiều users favorite
   - Many-to-Many relationship thông qua bảng favorites

**Ràng buộc toàn vẹn:**
- **Foreign keys:** Đảm bảo referential integrity
- **Unique constraints:** Tránh duplicate data (email, user+week, user+recipe favorite)
- **NOT NULL:** Đảm bảo required fields luôn có giá trị
- **Cascade actions:** Tự động xử lý khi parent record bị xóa

### 3.3.3. Sơ đồ ERD tổng quát

**Hình 3.19. Sơ đồ ERD Diagram của Recipe Discovery**

*(Xem chi tiết PlantUML ERD code trong file UML-DIAGRAMS.md)*

**Giải thích ERD:**

```
┌─────────┐ 1       N ┌──────────┐ N       1 ┌────────────────┐
│  users  │───creates─→│ recipes  │─categorized by─→│ user_categories│
└────┬────┘           └────┬─────┘           └────────────────┘
     │                     │
     │ 1:N                 │ 1:N
     │                     │
     ▼                     ▼
┌────────────┐ 1    N ┌────────────────┐
│ meal_plans │─contains─→│ meal_plan_items│
└────────────┘         └────────┬───────┘
                                │ N:1
                                │
                                ▼
                    Uses ┌──────────┐
                         │ recipes  │
                         └──────────┘

┌─────────┐ N           N ┌──────────┐
│  users  │────saves────→│ recipes  │
└─────────┘ (via favorites) └────────┘
```

**Cardinality chi tiết:**
- users ||--o{ recipes (1 user tạo 0-nhiều recipes)
- users ||--o{ user_categories (1 user có 0-nhiều categories)
- user_categories ||--o{ recipes (1 category chứa 0-nhiều recipes)
- users ||--o{ meal_plans (1 user có 0-nhiều meal plans)
- meal_plans ||--|{ meal_plan_items (1 plan chứa 1-nhiều items)
- recipes ||--o{ meal_plan_items (1 recipe dùng trong 0-nhiều items)
- users }o--o{ recipes (M:N qua favorites)

---

**Tổng kết:** Chương III đã trình bày chi tiết về phân tích bài toán, thiết kế Use Cases cho 5 nhóm chức năng chính và thiết kế cơ sở dữ liệu với 6 bảng và các mối quan hệ. Chương tiếp theo sẽ trình bày giao diện chương trình và các screenshots thực tế.
