CREATE DATABASE IF NOT EXISTS recipe_discovery
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE recipe_discovery;

-----------------------------------------------------
-- USERS
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  avatar_url VARCHAR(500) DEFAULT 'https://ui-avatars.com/api/?name=User&background=4caf50&color=fff',
  note VARCHAR(255) NULL,
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- CATEGORIES (hệ thống)
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS categories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  color_code VARCHAR(50) NOT NULL DEFAULT '#4caf50',
  icon VARCHAR(50) DEFAULT 'utensils',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- USER CUSTOM CATEGORIES
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    color_code VARCHAR(20),
    icon VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_category_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- RECIPES
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS recipes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  user_category_id BIGINT NULL,
  category_id BIGINT NULL,
  title VARCHAR(150) NOT NULL,
  ingredients TEXT NOT NULL,
  instructions TEXT NOT NULL,
  calories INT DEFAULT 0,
  cooking_time INT DEFAULT 0,
  servings INT DEFAULT 1,
  image_url TEXT,
  is_public TINYINT(1) NOT NULL DEFAULT 0,

  status VARCHAR(20) DEFAULT NULL,
  rejected_reason TEXT,

  share_status VARCHAR(20) NULL,
  share_approved_at DATETIME NULL,
  share_rejected_reason TEXT NULL,

  version INT NOT NULL DEFAULT 0,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_recipes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_recipes_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
  CONSTRAINT fk_recipes_user_category FOREIGN KEY (user_category_id) REFERENCES user_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- FAVORITES
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS favorites (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  recipe_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_fav (user_id, recipe_id),
  CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_fav_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- MEAL PLANS
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS meal_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    week_start_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY unique_user_week (user_id, week_start_date),
    CONSTRAINT fk_meal_plan_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- MEAL PLAN ITEMS
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS meal_plan_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    meal_plan_id BIGINT NOT NULL,
    day_of_week INT NOT NULL,               
    meal_type VARCHAR(20) NOT NULL,         
    recipe_id BIGINT NULL,                  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_item_plan FOREIGN KEY (meal_plan_id) REFERENCES meal_plans(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-----------------------------------------------------
-- INSERT DEFAULT ADMIN
-----------------------------------------------------
INSERT INTO users (full_name, email, password, role)
VALUES ('Admin', 'admin@recipe.com', 'admin123', 'ADMIN');

-- ---------------------------------------------------
-- RECIPE REVIEWS (Đánh giá & Bình luận công thức)
-- ---------------------------------------------------
CREATE TABLE IF NOT EXISTS recipe_reviews (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipe_id        BIGINT NOT NULL,
    user_id          BIGINT NOT NULL,
    rating           TINYINT NOT NULL DEFAULT 5 COMMENT '1-5 sao; 0 = reply',
    comment          TEXT,
    parent_id        BIGINT NULL COMMENT 'NULL = review gốc; top-level root id = reply',
    mention_user_id  BIGINT NULL COMMENT 'User được @mention khi reply',
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_recipe   FOREIGN KEY (recipe_id)       REFERENCES recipes(id)        ON DELETE CASCADE,
    CONSTRAINT fk_review_user     FOREIGN KEY (user_id)         REFERENCES users(id)          ON DELETE CASCADE,
    CONSTRAINT fk_review_parent   FOREIGN KEY (parent_id)       REFERENCES recipe_reviews(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_mention  FOREIGN KEY (mention_user_id) REFERENCES users(id)          ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------
-- REPORTS (Báo cáo vi phạm)
-- ---------------------------------------------------
CREATE TABLE IF NOT EXISTS reports (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id     BIGINT NOT NULL                 COMMENT 'User gửi báo cáo',
    target_type     VARCHAR(20) NOT NULL            COMMENT 'RECIPE hoặc REVIEW',
    target_id       BIGINT NOT NULL                 COMMENT 'ID của recipe/review bị báo cáo',
    reason          VARCHAR(100) NOT NULL           COMMENT 'Lý do báo cáo',
    description     TEXT                            COMMENT 'Mô tả chi tiết (tuỳ chọn)',
    status          VARCHAR(20) DEFAULT 'PENDING'   COMMENT 'PENDING / RESOLVED / REJECTED',
    admin_note      TEXT                            COMMENT 'Ghi chú xử lý của admin',
    processed_by    BIGINT NULL                     COMMENT 'Admin xử lý',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at    TIMESTAMP NULL,

    CONSTRAINT fk_report_reporter  FOREIGN KEY (reporter_id)  REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_report_processor FOREIGN KEY (processed_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------
-- SAMPLE DATA – tài khoản thaibao9714@gmail.com / 123456
-- ---------------------------------------------------

-- 1) User chính (Thái Bảo)
INSERT IGNORE INTO users (full_name, email, password, role, avatar_url) VALUES
('Nguyễn Thái Bảo', 'thaibao9714@gmail.com', '123456', 'USER',
 'https://ui-avatars.com/api/?name=Thai+Bao&background=4caf50&color=fff');

-- 2) Vài user phụ để tạo review đa dạng
INSERT IGNORE INTO users (full_name, email, password, role, avatar_url) VALUES
('Trần Minh Khoa', 'minhkhoa@example.com', 'pass', 'USER',
 'https://ui-avatars.com/api/?name=Minh+Khoa&background=2196f3&color=fff'),
('Lê Thị Thu Hương', 'thuhuong@example.com', 'pass', 'USER',
 'https://ui-avatars.com/api/?name=Thu+Huong&background=e91e63&color=fff'),
('Phạm Văn Đức', 'phamvanductest@example.com', 'pass', 'USER',
 'https://ui-avatars.com/api/?name=Van+Duc&background=ff9800&color=fff');

-- 3) 3 công thức APPROVED thuộc tài khoản Thái Bảo
--    (user_id dùng subquery để linh hoạt dù ID khác nhau trên mỗi DB)
INSERT INTO recipes
  (user_id, title, ingredients, instructions, calories, cooking_time, servings,
   image_url, is_public, share_status, share_approved_at)
SELECT id,
  'Phở Bò Hà Nội',
  '500g xương bò, 300g thịt bò, bánh phở, hành tây, gừng, gia vị',
  'Hầm xương 4-6 tiếng. Nướng hành tây và gừng. Trụng phở. Chan nước dùng.',
  450, 360, 4,
  'https://images.unsplash.com/photo-1614631446501-abcf76949a4b?w=800',
  1, 'APPROVED', NOW()
FROM users WHERE email='thaibao9714@gmail.com' LIMIT 1;

INSERT INTO recipes
  (user_id, title, ingredients, instructions, calories, cooking_time, servings,
   image_url, is_public, share_status, share_approved_at)
SELECT id,
  'Bánh Mì Thịt Nướng',
  'Bánh mì, thịt ba chỉ 300g, đồ chua, dưa leo, rau thơm, tương hoisin',
  'Ướp thịt 1 tiếng. Nướng 180°C 25 phút. Kẹp bánh mì với đồ chua và rau.',
  380, 45, 2,
  'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800',
  1, 'APPROVED', NOW()
FROM users WHERE email='thaibao9714@gmail.com' LIMIT 1;

INSERT INTO recipes
  (user_id, title, ingredients, instructions, calories, cooking_time, servings,
   image_url, is_public, share_status, share_approved_at)
SELECT id,
  'Bún Bò Huế',
  '500g bắp bò, chân giò heo, bún, sả, mắm ruốc, ớt, hành tím',
  'Hầm xương 3 tiếng với sả. Phi mắm ruốc. Nêm nếm. Chan lên bún.',
  520, 240, 5,
  'https://images.unsplash.com/photo-1555126634-323283e090fa?w=800',
  1, 'APPROVED', NOW()
FROM users WHERE email='thaibao9714@gmail.com' LIMIT 1;

-- 4) Reviews & Replies (7 review gốc + 3 reply)
--    recipe_id tham chiếu qua subquery theo title để linh hoạt

-- Review 1: Thái Bảo tự review Phở Bò của mình (✍️ Tác giả badge)
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 5, 'Phở này mình nấu theo công thức gia đình, nước dùng trong và ngọt tự nhiên. Rất tự hào!', NULL
FROM recipes r, users u
WHERE r.title='Phở Bò Hà Nội' AND u.email='thaibao9714@gmail.com' LIMIT 1;

-- Review 2: Minh Khoa review Phở Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 5, 'Công thức chuẩn vị, mình làm thử lần đầu mà ra đúng vị phở Hà Nội thật sự. 5 sao!', NULL
FROM recipes r, users u
WHERE r.title='Phở Bò Hà Nội' AND u.email='minhkhoa@example.com' LIMIT 1;

-- Review 3: Thu Hương review Phở Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 4, 'Ngon nhưng mình thấy cần thêm ít quế hơn cho phù hợp khẩu vị nhà mình.', NULL
FROM recipes r, users u
WHERE r.title='Phở Bò Hà Nội' AND u.email='thuhuong@example.com' LIMIT 1;

-- Review 4: Minh Khoa review Bánh Mì
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 5, 'Thịt nướng thơm lừng, đồ chua tự làm giòn giòn ngon lắm. Cả nhà ai cũng thích!', NULL
FROM recipes r, users u
WHERE r.title='Bánh Mì Thịt Nướng' AND u.email='minhkhoa@example.com' LIMIT 1;

-- Review 5: Thu Hương review Bánh Mì
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 4, 'Ngon, nhưng mình đổi tương hoisin thành tương sriracha cho thêm cay. Hợp hơn!', NULL
FROM recipes r, users u
WHERE r.title='Bánh Mì Thịt Nướng' AND u.email='thuhuong@example.com' LIMIT 1;

-- Review 6: Phạm Văn Đức review Bún Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 5, 'Đậm vị miền Trung, mắm ruốc vừa phải không quá nặng. Tuyệt vời!', NULL
FROM recipes r, users u
WHERE r.title='Bún Bò Huế' AND u.email='phamvanductest@example.com' LIMIT 1;

-- Review 7: Thái Bảo tự review Bún Bò của mình (✍️ Tác giả badge)
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT r.id, u.id, 5, 'Công thức này mình được bà ngoại truyền lại. Vị chuẩn Huế 100%!', NULL
FROM recipes r, users u
WHERE r.title='Bún Bò Huế' AND u.email='thaibao9714@gmail.com' LIMIT 1;

-- Reply 1: Thái Bảo reply lại review của Minh Khoa trên Phở Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT rr.recipe_id, u.id, 0, 'Cảm ơn bạn đã thử! Mình rất vui khi công thức hợp khẩu vị nhà bạn 🙏', rr.id
FROM recipe_reviews rr, users u
WHERE rr.comment LIKE '%lần đầu mà ra đúng vị phở%' AND u.email='thaibao9714@gmail.com' LIMIT 1;

-- Reply 2: Minh Khoa reply lại Thu Hương trên Phở Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT rr.recipe_id, u.id, 0, 'Đúng rồi, mình cũng giảm quế đi thì thấy nhẹ hơn nhiều!', rr.id
FROM recipe_reviews rr, users u
WHERE rr.comment LIKE '%cần thêm ít quế hơn%' AND u.email='minhkhoa@example.com' LIMIT 1;

-- Reply 3: Thái Bảo reply lại Phạm Văn Đức trên Bún Bò
INSERT INTO recipe_reviews (recipe_id, user_id, rating, comment, parent_id)
SELECT rr.recipe_id, u.id, 0, 'Cảm ơn bạn! Bí quyết là phi mắm ruốc với dầu thật thơm trước khi cho vào nồi 😊', rr.id
FROM recipe_reviews rr, users u
WHERE rr.comment LIKE '%mắm ruốc vừa phải%' AND u.email='thaibao9714@gmail.com' LIMIT 1;

-- ---------------------------------------------------
-- SAMPLE REPORTS (Báo cáo vi phạm mẫu)
-- ---------------------------------------------------

-- Báo cáo 1: Minh Khoa báo cáo công thức Phở Bò
INSERT INTO reports (reporter_id, target_type, target_id, reason, description, status)
SELECT u.id, 'RECIPE', r.id, 'Thông tin sai lệch',
       'Công thức có thành phần không đúng, gây hiểu nhầm cho người đọc.', 'PENDING'
FROM users u, recipes r
WHERE u.email='minhkhoa@example.com' AND r.title='Phở Bò Hà Nội' LIMIT 1;

-- Báo cáo 2: Thu Hương báo cáo công thức Bánh Mì
INSERT INTO reports (reporter_id, target_type, target_id, reason, description, status)
SELECT u.id, 'RECIPE', r.id, 'Ảnh hưởng sức khỏe',
       'Công thức dùng quá nhiều dầu và muối, có thể ảnh hưởng sức khỏe.', 'PENDING'
FROM users u, recipes r
WHERE u.email='thuhuong@example.com' AND r.title='Bánh Mì Thịt Nướng' LIMIT 1;

-- Báo cáo 3: Phạm Văn Đức báo cáo review của Thái Bảo
INSERT INTO reports (reporter_id, target_type, target_id, reason, description, status)
SELECT u.id, 'REVIEW', rr.id, 'Spam / quảng cáo',
       'Bình luận này có vẻ như quảng cáo sản phẩm.', 'PENDING'
FROM users u, recipe_reviews rr
WHERE u.email='phamvanductest@example.com'
  AND rr.comment LIKE '%gia đình%' LIMIT 1;

-- Báo cáo 4 (đã xử lý – làm mẫu RESOLVED)
INSERT INTO reports (reporter_id, target_type, target_id, reason, status, admin_note, processed_by, processed_at)
SELECT u.id, 'REVIEW', rr.id, 'Ngôn từ thô tục',
       'RESOLVED', 'Đã xem xét và xóa nội dung vi phạm.',
       (SELECT id FROM users WHERE email='admin@recipe.com' LIMIT 1),
       NOW()
FROM users u, recipe_reviews rr
WHERE u.email='minhkhoa@example.com'
  AND rr.comment LIKE '%Vị chuẩn Huế%' LIMIT 1;

-- Báo cáo 5 (đã từ chối – làm mẫu REJECTED)
INSERT INTO reports (reporter_id, target_type, target_id, reason, status, admin_note, processed_by, processed_at)
SELECT u.id, 'RECIPE', r.id, 'Vi phạm bản quyền',
       'REJECTED', 'Nội dung hợp lệ, không vi phạm bản quyền.',
       (SELECT id FROM users WHERE email='admin@recipe.com' LIMIT 1),
       NOW()
FROM users u, recipes r
WHERE u.email='thuhuong@example.com' AND r.title='Bún Bò Huế' LIMIT 1;


