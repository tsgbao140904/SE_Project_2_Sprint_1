# TÀI LIỆU BÁO CÁO BẢO VỆ KHÓA LUẬN TỐT NGHIỆP

**Đề tài:** HỆ THỐNG QUẢN LÝ VÀ CHIA SẺ CÔNG THỨC NẤU ĂN (RECIPE DISCOVERY)

_Tài liệu này được viết bằng tiếng Việt tự nhiên, dễ hiểu, có giải thích chi tiết về kiến trúc và luồng xử lý_

---

# MỤC LỤC

1. [Giới thiệu đề tài](#1-giới-thiệu-đề-tài)
2. [Kiến trúc hệ thống](#2-kiến-trúc-hệ-thống)
3. [Luồng xử lý chi tiết](#3-luồng-xử-lý-chi-tiết)
4. [Yêu cầu và chức năng](#4-yêu-cầu-và-chức-năng)
5. [Thiết kế cơ sở dữ liệu](#5-thiết-kế-cơ-sở-dữ-liệu)
6. [Xây dựng và triển khai](#6-xây-dựng-và-triển-khai)
7. [Kết quả và kết luận](#7-kết-quả-và-kết-luận)

---

# 1. GIỚI THIỆU ĐỀ TÀI

## 1.1. Đặt vấn đề

Thưa thầy/cô, em nhận thấy hiện nay những người yêu thích nấu ăn đang gặp một số khó khăn sau:

**📝 Vấn đề về quản lý công thức:**
- Công thức được lưu rải rác (sổ tay, ảnh chụp, ghi chú điện thoại...)
- Khó tìm kiếm khi cần
- Dễ thất lạc thông tin
- Không có cách phân loại khoa học

**📅 Vấn đề về kế hoạch bữa ăn:**
- Lập kế hoạch nấu gì trong tuần rất mất thời gian
- Dễ quên những món đã định nấu
- Không có thông báo nhắc nhở
- Khó theo dõi dinh dưỡng

**👥 Vấn đề về chia sẻ:**
- Thiếu nền tảng để chia sẻ món ăn ngon với người khác
- Khó khám phá món ăn mới
- Không có cộng đồng yêu thích nấu ăn

## 1.2. Giải pháp của em

Xuất phát từ những vấn đề trên, em đã xây dựng hệ thống **Recipe Discovery** (tạm dịch: Khám phá công thức nấu ăn) với các tính năng chính:

✅ **Quản lý công thức tập trung** - Lưu trữ tất cả công thức ở một nơi  
✅ **Lập kế hoạch bữa ăn theo tuần** - Xếp lịch trực quan cho 7 ngày  
✅ **Email thông báo tự động** - Nhắc nhở 4 lần mỗi ngày  
✅ **Cộng đồng chia sẻ** - Chia sẻ và học hỏi lẫn nhau

## 1.3. Mục tiêu đề tài

**Mục tiêu tổng quát:**
Xây dựng một trang web giúp mọi người quản lý công thức nấu ăn và chia sẻ với nhau một cách dễ dàng, khoa học.

**Mục tiêu cụ thể:**

1. **Về quản lý công thức:**
   - Cho phép thêm/sửa/xóa công thức
   - Upload ảnh món ăn
   - Phân loại theo danh mục riêng
   - Tìm kiếm và lọc công thức

2. **Về lập kế hoạch:**
   - Xếp lịch cho cả tuần (7 ngày)
   - Chia thành 4 bữa: Sáng - Trưa - Phụ - Tối
   - Giao diện lịch dễ nhìn

3. **Về thông báo:**
   - Gửi email tự động 4 lần/ngày
   - Đúng giờ (7h, 11h, 15h, 17h)

4. **Về cộng đồng:**
   - Chia sẻ công thức công khai
   - Xem và lưu món yêu thích

## 1.4. Công nghệ sử dụng

Em sử dụng các công nghệ sau đây, thầy/cô ạ:

### Phần máy chủ (Backend - chạy ngầm):

**☕ Java 17**
- Là ngôn ngữ lập trình chính em dùng
- Ổn định, được nhiều công ty sử dụng
- Có sẵn nhiều thư viện hỗ trợ

**🍃 Spring Boot 3.2.2**
- Là khung công nghệ chính để xây dựng trang web
- Giúp tự động cấu hình, giảm code thủ công
- Bao gồm các module con:
  - **Spring MVC:** Xử lý yêu cầu từ trình duyệt
  - **Spring Data JPA:** Kết nối và làm việc với cơ sở dữ liệu
  - **Spring Mail:** Gửi email
  - **Spring Scheduler:** Chạy các tác vụ định kỳ

**🗄️ MySQL 8.0**
- Là hệ quản trị cơ sở dữ liệu
- Miễn phí, phổ biến nhất thế giới
- Hỗ trợ tiếng Việt tốt

### Phần giao diện (Frontend - người dùng thấy):

**🎨 Thymeleaf**
- Công cụ tạo trang web động
- Kết hợp HTML với dữ liệu từ máy chủ
- Tích hợp tốt với Spring Boot

**💅 Bootstrap 5.3.3**
- Khung giao diện đẹp, hiện đại
- Tự động điều chỉnh trên điện thoại/máy tính/tablet
- Có sẵn nhiều thành phần đẹp (nút, form, bảng...)

**⚡ JavaScript**
- Tạo tính tương tác cho trang web
- Xử lý sự kiện (click, nhập liệu...)

### Gửi email:

**📧 Gmail SMTP**
- Sử dụng tài khoản Gmail để gửi thông báo
- Miễn phí, đáng tin cậy
- Kết nối qua cổng 587

---

# 2. KIẾN TRÚC HỆ THỐNG

## 2.1. Mô hình kiến trúc tổng quát

Em sử dụng mô hình **MVC (Model-View-Controller)** kết hợp với **Service Layer** và **Repository Layer**. Đây là mô hình phân tầng được sử dụng rộng rãi trong phát triển web.

### Sơ đồ tổng quan:

```
┌─────────────────────────────────────────┐
│    TẦNG 1: GIAO DIỆN (VIEW)             │
│    - Thymeleaf Templates                │
│    - HTML/CSS/JavaScript                │
│    - Bootstrap Framework                │
└──────────────┬──────────────────────────┘
               │ HTTP Request/Response
               ↓
┌─────────────────────────────────────────┐
│    TẦNG 2: ĐIỀU KHIỂN (CONTROLLER)      │
│    - Nhận yêu cầu từ người dùng         │
│    - Kiểm tra dữ liệu đầu vào           │
│    - Gọi Service xử lý                  │
└──────────────┬──────────────────────────┘
               │ DTO (Data Transfer Object)
               ↓
┌─────────────────────────────────────────┐
│    TẦNG 3: NGHIỆP VỤ (SERVICE)          │
│    - Chứa logic xử lý chính             │
│    - Áp dụng quy tắc nghiệp vụ          │
│    - Quản lý giao dịch                  │
└──────────────┬──────────────────────────┘
               │ Entity (Đối tượng thực thể)
               ↓
┌─────────────────────────────────────────┐
│    TẦNG 4: TRUY CẬP DỮ LIỆU (REPO)     │
│    - Kết nối cơ sở dữ liệu              │
│    - Tạo câu lệnh SQL tự động           │
│    - Thực thi truy vấn                  │
└──────────────┬──────────────────────────┘
               │ SQL Query
               ↓
┌─────────────────────────────────────────┐
│    TẦNG 5: CƠ SỞ DỮ LIỆU (DATABASE)    │
│    - MySQL 8.0                          │
│    - Lưu trữ dữ liệu vĩnh viễn          │
└─────────────────────────────────────────┘
```

## 2.2. Giải thích chi tiết từng tầng

### TẦNG 1: Tầng Giao diện (Presentation Layer)

**Vai trò:**
- Hiển thị thông tin cho người dùng
- Nhận thông tin đầu vào từ người dùng
- Là phần người dùng nhìn thấy và tương tác

**Công nghệ:**
- Thymeleaf templates (17 file HTML)
- Bootstrap 5.3.3 (CSS framework)
- JavaScript (xử lý sự kiện)

**Ví dụ các trang:**
- `login.html` - Trang đăng nhập
- `home.html` - Trang danh sách công thức
- `recipe-form.html` - Form thêm/sửa công thức
- `meal-plan.html` - Lịch bữa ăn trong tuần

---

### TẦNG 2: Tầng Điều khiển (Controller Layer)

**Vai trò:**
- Nhận HTTP request từ trình duyệt
- Kiểm tra dữ liệu đầu vào (validation)
- Gọi Service để xử lý
- Chuẩn bị dữ liệu cho View
- Trả về tên trang hoặc chuyển hướng

**Công nghệ:**
- Spring MVC
- Annotation: `@Controller`, `@GetMapping`, `@PostMapping`

**Số lượng:** 10+ controllers

**Ví dụ controllers:**
- `AuthController` - Xử lý đăng nhập/đăng ký
- `UserRecipeController` - Quản lý công thức
- `MealPlanController` - Quản lý lịch bữa ăn
- `CommunityController` - Cộng đồng chia sẻ
- `AdminController` - Quản trị hệ thống

**Ví dụ code:**
```java
@Controller
public class UserRecipeController {
    
    @PostMapping("/app/recipes/new")
    public String create(@ModelAttribute RecipeRequest request) {
        // Gọi service xử lý
        recipeService.createForUser(userId, request);
        // Chuyển hướng về trang chủ
        return "redirect:/app/home?created";
    }
}
```

---

### TẦNG 3: Tầng Nghiệp vụ (Service Layer)

**Vai trò:**
- Chứa toàn bộ logic xử lý của hệ thống
- Áp dụng các quy tắc nghiệp vụ
- Quản lý giao dịch (transaction)
- Gọi nhiều Repository nếu cần
- Chuyển đổi giữa DTO và Entity

**Công nghệ:**
- Spring Service
- Annotation: `@Service`, `@Transactional`

**Số lượng:** 6+ services

**Ví dụ services:**
- `RecipeService` - Logic quản lý công thức
- `MealPlanService` - Logic lập kế hoạch
- `EmailService` - Logic gửi email
- `AuthService` - Logic xác thực
- `UserService` - Logic quản lý người dùng

**Ví dụ code:**
```java
@Service
public class RecipeService {
    
    @Transactional
    public Recipe createForUser(Long userId, RecipeRequest request) {
        // 1. Xử lý upload ảnh
        String imageUrl = handleImageUpload(request.getImageFile());
        
        // 2. Tạo entity từ DTO
        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setTitle(request.getTitle());
        recipe.setImageUrl(imageUrl);
        // ... set các field khác
        
        // 3. Lưu vào database
        return recipeRepository.save(recipe);
    }
}
```

---

### TẦNG 4: Tầng Truy cập dữ liệu (Repository Layer)

**Vai trò:**
- Kết nối trực tiếp với cơ sở dữ liệu
- Tự động tạo câu lệnh SQL
- Thực thi truy vấn (SELECT, INSERT, UPDATE, DELETE)
- KHÔNG chứa logic nghiệp vụ

**Công nghệ:**
- Spring Data JPA
- Kế thừa từ `JpaRepository`

**Số lượng:** 6+ repositories

**Ví dụ repositories:**
- `RecipeRepository`
- `UserRepository`
- `MealPlanRepository`
- `MealPlanItemRepository`

**Ví dụ code:**
```java
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    // Tự động tạo SQL: SELECT * FROM recipes WHERE user_id = ?
    List<Recipe> findByUserId(Long userId);
    
    // Tự viết SQL bằng @Query
    @Query("SELECT r FROM Recipe r WHERE r.calories <= :maxCalo")
    List<Recipe> findByCaloriesLessThan(int maxCalo);
}
```

---

### TẦNG 5: Cơ sở dữ liệu (Database Layer)

**Vai trò:**
- Lưu trữ dữ liệu vĩnh viễn
- Thực thi câu lệnh SQL
- Đảm bảo tính toàn vẹn dữ liệu

**Công nghệ:**
- MySQL 8.0
- Character set: UTF8MB4 (hỗ trợ tiếng Việt)
- Storage engine: InnoDB

**Số lượng:** 7 bảng chính

---

# 3. LUỒNG XỬ LÝ CHI TIẾT

## 3.1. Ví dụ 1: Người dùng tạo công thức mới

Để thầy/cô hiểu rõ hơn, em xin phép trình bày chi tiết luồng xử lý khi người dùng tạo một công thức mới:

### Bước 1: Người dùng điền form

**Diễn ra ở:** Tầng Giao diện  
**File:** `recipe-form.html`

Người dùng:
1. Click nút "Tạo công thức mới"
2. Mở trang form
3. Điền thông tin:
   - Tên món: "Phở bò"
   - Nguyên liệu: "Thịt bò, bánh phở, hành..."
   - Cách nấu: "Bước 1: Luộc xương..."
   - Chọn ảnh từ máy tính
   - Nhập calo: 450
   - Thời gian nấu: 90 phút
4. Click nút "Lưu"

### Bước 2: Trình duyệt gửi yêu cầu

**Diễn ra ở:** Kết nối Client-Server

- Trình duyệt tạo HTTP POST request
- URL: `http://localhost:8080/app/recipes/new`
- Gửi dữ liệu form lên máy chủ
- Bao gồm cả file ảnh (nếu có)

### Bước 3: Controller nhận yêu cầu

**Diễn ra ở:** Tầng Điều khiển  
**Class:** `UserRecipeController`

```java
@PostMapping("/app/recipes/new")
public String create(@ModelAttribute RecipeRequest request, 
                     HttpSession session) {
    
    // 1. Lấy thông tin user từ session
    SessionUser user = (SessionUser) session.getAttribute("USER");
    
    // 2. Kiểm tra dữ liệu đầu vào
    if (request.getTitle() == null || request.getTitle().isEmpty()) {
        return "redirect:/app/recipes/new?error=titleRequired";
    }
    
    // 3. Gọi service xử lý
    recipeService.createForUser(user.getId(), request);
    
    // 4. Chuyển hướng về trang chủ với thông báo thành công
    return "redirect:/app/home?created";
}
```

**Controller làm gì:**
- Nhận dữ liệu từ form
- Lấy ID người dùng từ session
- Kiểm tra xem tên món có để trống không
- Gọi `RecipeService` để xử lý
- Chuyển hướng về trang chủ

### Bước 4: Service xử lý logic nghiệp vụ

**Diễn ra ở:** Tầng Nghiệp vụ  
**Class:** `RecipeService`

```java
@Service
public class RecipeService {
    
    @Transactional
    public Recipe createForUser(Long userId, RecipeRequest request) {
        
        // 1. Xử lý upload ảnh
        String imageUrl = null;
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            // Lưu file vào thư mục uploads/
            imageUrl = saveImageToUploadsFolder(request.getImageFile());
        }
        
        // 2. Tạo entity Recipe
        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setUserCategoryId(request.getUserCategoryId());
        recipe.setTitle(request.getTitle());
        recipe.setIngredients(request.getIngredients());
        recipe.setInstructions(request.getInstructions());
        recipe.setImageUrl(imageUrl);
        recipe.setCalories(request.getCalories());
        recipe.setCookingTime(request.getCookingTime());
        recipe.setServings(request.getServings());
        recipe.setIsPublic(0); // Mặc định là riêng tư
        
        // 3. Gọi repository để lưu
        Recipe savedRecipe = recipeRepository.save(recipe);
        
        // 4. Trả về recipe đã lưu
        return savedRecipe;
    }
}
```

**Service làm gì:**
- Xử lý upload ảnh (lưu vào thư mục `uploads/`)
- Chuyển đổi từ `RecipeRequest` (DTO) sang `Recipe` (Entity)
- Set các giá trị mặc định
- Gọi Repository để lưu vào database

### Bước 5: Repository tương tác với database

**Diễn ra ở:** Tầng Truy cập dữ liệu  
**Interface:** `RecipeRepository`

```java
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Không cần viết code, Spring tự động tạo
}
```

**Repository làm gì:**
- Spring Data JPA tự động tạo câu lệnh SQL INSERT
- Câu SQL tương đương:
```sql
INSERT INTO recipes (
    user_id, user_category_id, title, ingredients, 
    instructions, image_url, calories, cooking_time, 
    servings, is_public, created_at, updated_at
) VALUES (
    1, 2, 'Phở bò', 'Thịt bò, bánh phở...', 
    'Bước 1: Luộc xương...', '/images/pho-bo.jpg', 
    450, 90, 2, 0, NOW(), NOW()
);
```
- Thực thi câu SQL trên MySQL

### Bước 6: MySQL lưu dữ liệu

**Diễn ra ở:** Cơ sở dữ liệu

- MySQL nhận câu lệnh INSERT
- Kiểm tra ràng buộc (foreign key, NOT NULL...)
- Lưu dữ liệu vào bảng `recipes`
- Tự động tạo ID (auto increment): ví dụ ID = 15
- Trả về bản ghi đã lưu

### Bước 7: Kết quả trả về ngược lên

**Luồng ngược:**

```
MySQL → Repository → Service → Controller → View
```

- **Repository** trả về `Recipe` (ID=15) cho Service
- **Service** trả về `Recipe` cho Controller
- **Controller** chuyển hướng: `redirect:/app/home?created`
- **View** hiển thị trang chủ với thông báo "Tạo công thức thành công!"

### Bước 8: Người dùng thấy kết quả

**Hiển thị:**
- Trang chủ được load lại
- Hiển thị thông báo màu xanh: "✅ Tạo công thức thành công!"
- Công thức "Phở bò" xuất hiện trong danh sách

---

## 3.2. Ví dụ 2: Hệ thống gửi email tự động

Đây là một luồng đặc biệt vì không do người dùng kích hoạt, mà chạy tự động theo lịch.

### Bước 1: Scheduler kích hoạt (7h sáng)

**Diễn ra ở:** Background Service  
**Class:** `MealPlanNotificationScheduler`

```java
@Component
public class MealPlanNotificationScheduler {
    
    @Scheduled(cron = "0 0 7 * * ?") // 7:00 AM hàng ngày
    public void sendBreakfastNotifications() {
        
        String mealType = "BREAKFAST";
        int dayOfWeek = getCurrentDayOfWeek(); // Ví dụ: 1 (Thứ 2)
        
        // Gọi email service
        emailService.sendMealNotifications(mealType, dayOfWeek);
    }
}
```

**Scheduler làm gì:**
- Chạy tự động vào 7h sáng mỗi ngày
- Xác định ngày trong tuần (1=Thứ 2, 2=Thứ 3,...)
- Gọi EmailService để gửi thông báo bữa sáng

### Bước 2: Email Service xử lý

**Diễn ra ở:** Tầng Nghiệp vụ  
**Class:** `EmailService`

```java
@Service
public class EmailService {
    
    @Transactional
    public void sendMealNotifications(String mealType, int dayOfWeek) {
        
        // 1. Lấy danh sách tất cả người dùng
        List<User> allUsers = userRepository.findAll();
        
        int sentCount = 0;
        
        // 2. Với mỗi người dùng
        for (User user : allUsers) {
            
            // 3. Tìm meal plan tuần hiện tại
            MealPlan mealPlan = findCurrentWeekPlan(user.getId());
            
            if (mealPlan == null) continue; // Bỏ qua nếu không có plan
            
            // 4. Tìm món ăn cho slot cụ thể
            MealPlanItem item = findMealItem(mealPlan, dayOfWeek, mealType);
            
            if (item == null || item.getRecipe() == null) continue;
            
            // 5. Gửi email
            boolean sent = sendEmail(user, item.getRecipe(), mealType);
            
            if (sent) sentCount++;
        }
        
        // 6. Ghi log
        log.info("Đã gửi " + sentCount + " email cho bữa " + mealType);
    }
}
```

**Email Service làm gì:**
- Lấy tất cả người dùng từ database
- Với mỗi người:
  - Tìm kế hoạch tuần hiện tại
  - Tìm món ăn cho slot (Thứ 2 + Bữa sáng)
  - Nếu có món → Gửi email
- Đếm số email đã gửi

### Bước 3: Gửi email qua Gmail

**Diễn ra ở:** Email Service  

```java
private boolean sendEmail(User user, Recipe recipe, String mealType) {
    try {
        // 1. Tạo email message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        // 2. Set thông tin email
        helper.setTo(user.getEmail());
        helper.setSubject("🍳 Bữa " + translateMealType(mealType) + " hôm nay");
        
        // 3. Render template HTML
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("recipe", recipe);
        context.setVariable("mealType", mealType);
        
        String htmlContent = templateEngine.process("meal-notification-email", context);
        helper.setText(htmlContent, true);
        
        // 4. Gửi email
        mailSender.send(message);
        
        return true;
        
    } catch (Exception e) {
        log.error("Lỗi gửi email cho " + user.getEmail(), e);
        return false;
    }
}
```

**Gửi email làm gì:**
- Tạo email message
- Set người nhận, tiêu đề
- Render template HTML với dữ liệu món ăn
- Gửi qua Gmail SMTP (cổng 587)

---

## 3.3. Tổng kết luồng xử lý

**Điểm chung của các luồng:**

1. **Luồng từ trên xuống:**
   - View → Controller → Service → Repository → Database

2. **Luồng từ dưới lên:**
   - Database → Repository → Service → Controller → View

3. **Nguyên tắc:**
   - Mỗi tầng chỉ giao tiếp với tầng liền kề
   - Controller KHÔNG gọi trực tiếp Repository
   - View KHÔNG gọi trực tiếp Service

4. **Lợi ích:**
   - Dễ bảo trì: Sửa một tầng không ảnh hưởng tầng khác
   - Dễ test: Test từng tầng độc lập
   - Dễ mở rộng: Thêm chức năng mới dễ dàng

---

# 4. YÊU CẦU VÀ CHỨC NĂNG

## 4.1. Người dùng hệ thống

**1. 👤 Người dùng thường (USER):**
- Đăng ký, đăng nhập, đăng xuất
- Quản lý công thức cá nhân
- Lập kế hoạch bữa ăn
- Chia sẻ và xem công thức cộng đồng
- Nhận email thông báo

**2. 👨‍💼 Quản trị viên (ADMIN):**
- Có tất cả quyền của người dùng thường
- Quản lý tài khoản (khóa/mở khóa)
- Kiểm duyệt nội dung chia sẻ
- Xem thống kê hệ thống

**3. ⏰ Hệ thống tự động (SYSTEM):**
- Gửi email 4 lần/ngày
- Chạy nền, không cần đăng nhập

## 4.2. Danh sách chức năng

Em đã xây dựng **hơn 20 chức năng**, chia thành 7 nhóm:

### Nhóm 1: Xác thực (3 chức năng)
- Đăng ký tài khoản mới
- Đăng nhập hệ thống
- Đăng xuất

### Nhóm 2: Quản lý công thức (6 chức năng)
- Tạo công thức mới
- Sửa công thức
- Xóa công thức
- Xem danh sách công thức
- Xem chi tiết công thức
- Tìm kiếm và lọc

### Nhóm 3: Kế hoạch bữa ăn (4 chức năng)
- Xem lịch tuần
- Thêm món vào slot
- Xóa món khỏi slot
- Tự động tạo lịch tuần mới

### Nhóm 4: Email tự động (1 chức năng)
- Gửi thông báo 4 lần/ngày

### Nhóm 5: Cộng đồng (3 chức năng)
- Chia sẻ công thức công khai
- Xem công thức người khác
- Lưu món yêu thích

### Nhóm 6: Quản trị (3 chức năng)
- Quản lý người dùng
- Kiểm duyệt nội dung
- Xem dashboard

### Nhóm 7: Danh mục (2 chức năng)
- Tạo danh mục riêng
- Sửa/xóa danh mục

---

# 5. THIẾT KẾ CƠ SỞ DỮ LIỆU

## 5.1. Tổng quan

**Thông tin chung:**
- Tên database: `recipe_discovery`
- Hỗ trợ tiếng Việt: ✅ (UTF8MB4)
- Số bảng: **7 bảng chính**
- Công cụ: InnoDB (đảm bảo an toàn giao dịch)

## 5.2. Danh sách bảng

1. **users** - Lưu thông tin người dùng
2. **recipes** - Lưu công thức nấu ăn
3. **meal_plans** - Lưu kế hoạch theo tuần
4. **meal_plan_items** - Lưu chi tiết từng món trong lịch
5. **user_categories** - Danh mục riêng của người dùng
6. **favorites** - Món yêu thích
7. **categories** - Danh mục hệ thống

## 5.3. Mối quan hệ giữa các bảng

```
NGƯỜI DÙNG (users)
    ├── tạo nhiều → CÔNG THỨC (recipes)
    ├── tạo nhiều → DANH MỤC (user_categories)
    ├── tạo nhiều → KẾ HOẠCH TUẦN (meal_plans)
    └── lưu nhiều → MÓN YÊU THÍCH (favorites)

KẾ HOẠCH TUẦN (meal_plans)
    └── chứa nhiều → CHI TIẾT MÓN (meal_plan_items)
                        └── tham chiếu → CÔNG THỨC (recipes)

DANH MỤC (user_categories)
    └── chứa nhiều → CÔNG THỨC (recipes)
```

## 5.4. Bảng quan trọng nhất: recipes

**Cấu trúc bảng recipes:**

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | BIGINT | Mã công thức (tự động tăng) |
| user_id | BIGINT | Người tạo (liên kết bảng users) |
| user_category_id | BIGINT | Danh mục |
| title | VARCHAR(150) | Tên món ăn |
| ingredients | TEXT | Nguyên liệu (văn bản dài) |
| instructions | TEXT | Cách nấu (văn bản dài) |
| image_url | VARCHAR(500) | Đường dẫn ảnh |
| calories | INT | Lượng calo |
| cooking_time | INT | Thời gian nấu (phút) |
| servings | INT | Số người ăn |
| is_public | INT | Công khai? (0/1) |
| share_status | VARCHAR(20) | PENDING/APPROVED/REJECTED |
| created_at | TIMESTAMP | Ngày tạo |
| updated_at | TIMESTAMP | Ngày sửa |

---

# 6. XÂY DỰNG VÀ TRIỂN KHAI

## 6.1. Môi trường làm việc

**Công cụ em sử dụng:**

| Công cụ | Phiên bản | Mục đích |
|---------|-----------|----------|
| IntelliJ IDEA | 2024 | Viết code Java |
| Java | 17 | Ngôn ngữ lập trình |
| Maven | 3.9 | Quản lý thư viện |
| MySQL | 8.0 | Cơ sở dữ liệu |
| Git | Latest | Quản lý phiên bản code |

## 6.2. Cách chạy ứng dụng

**Cách 1: Từ IntelliJ IDEA**
1. Mở project
2. Chạy file `RecipeDiscoveryApplication.java`
3. Truy cập: `http://localhost:8080`

**Cách 2: Từ dòng lệnh**
```bash
# Đóng gói
mvn clean install

# Chạy
java -jar target/recipe-discovery-1.0.0.jar
```

## 6.3. Cấu hình

**File: `application.yml`**

```yaml
# Cổng web
server:
  port: 8080

# Kết nối MySQL
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recipe_discovery
    username: root
    password: [mật khẩu của bạn]

# Email Gmail
  mail:
    host: smtp.gmail.com
    port: 587
    username: [email của bạn]
    password: [mật khẩu ứng dụng]
```

---

# 7. KẾT QUẢ VÀ KẾT LUẬN

## 7.1. Kết quả đạt được

✅ **Về sản phẩm:**
- Trang web chạy ổn định
- Giao diện đẹp, responsive
- Email gửi đúng giờ
- Hơn 20 chức năng hoạt động tốt

✅ **Về kỹ thuật:**
- 17 trang giao diện
- 7 bảng cơ sở dữ liệu
- 10+ controllers
- 6+ services
- Thời gian phản hồi < 1 giây

## 7.2. Hạn chế

⚠️ Mật khẩu chưa mã hóa  
⚠️ Chưa có bộ nhớ đệm  
⚠️ Chưa có ứng dụng di động

## 7.3. Hướng phát triển

1. Mã hóa mật khẩu với BCrypt
2. Thêm Redis cache
3. Làm ứng dụng điện thoại
4. Tích hợp AI gợi ý món

## 7.4. Kết luận

Qua khóa luận này, em đã:
- Hiểu rõ kiến trúc phân tầng MVC
- Thành thạo Spring Boot
- Biết thiết kế database
- Rèn luyện kỹ năng giải quyết vấn đề

---

# CẢM ƠN

Em xin chân thành cảm ơn thầy/cô đã lắng nghe. Em sẵn sàng trả lời câu hỏi ạ!

---

_Hết phần trình bày_
