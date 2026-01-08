package com.example.recipediscovery.scheduler;

import com.example.recipediscovery.model.MealPlan;
import com.example.recipediscovery.model.MealPlanItem;
import com.example.recipediscovery.model.Recipe;
import com.example.recipediscovery.model.User;
import com.example.recipediscovery.repository.MealPlanItemRepository;
import com.example.recipediscovery.repository.MealPlanRepository;
import com.example.recipediscovery.repository.UserRepository;
import com.example.recipediscovery.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MealPlanNotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MealPlanNotificationScheduler.class);

    private final UserRepository userRepository;
    private final MealPlanRepository mealPlanRepository;
    private final MealPlanItemRepository mealPlanItemRepository;
    private final EmailService emailService;

    public MealPlanNotificationScheduler(UserRepository userRepository,
                                          MealPlanRepository mealPlanRepository,
                                          MealPlanItemRepository mealPlanItemRepository,
                                          EmailService emailService) {
        this.userRepository = userRepository;
        this.mealPlanRepository = mealPlanRepository;
        this.mealPlanItemRepository = mealPlanItemRepository;
        this.emailService = emailService;
    }

    /**
     * Gửi thông báo bữa sáng lúc 07:00 mỗi ngày
     */
    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 7 * * ?")
    public void sendBreakfastNotifications() {
        logger.info("🍳 Starting BREAKFAST notifications at: {}", LocalDateTime.now());
        sendNotificationsForMeal("BREAKFAST");
    }

    /**
     * Gửi thông báo bữa trưa lúc 11:00 mỗi ngày
     */
    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 11 * * ?")
    public void sendLunchNotifications() {
        logger.info("🍽️ Starting LUNCH notifications at: {}", LocalDateTime.now());
        sendNotificationsForMeal("LUNCH");
    }

    /**
     * Gửi thông báo bữa phụ lúc 15:00 mỗi ngày
     */
    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 15 * * ?")
    public void sendSnackNotifications() {
        logger.info("☕ Starting SNACK notifications at: {}", LocalDateTime.now());
        sendNotificationsForMeal("SNACK");
    }

    /**
     * Gửi thông báo bữa tối lúc 17:00 mỗi ngày
     */
    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 17 * * ?")
    public void sendDinnerNotifications() {
        logger.info("🌙 Starting DINNER notifications at: {}", LocalDateTime.now());
        sendNotificationsForMeal("DINNER");
    }

    /**
     * Logic chung để gửi notification cho một bữa ăn cụ thể
     */
    private void sendNotificationsForMeal(String mealType) {
        int sentCount = 0;

        try {
            // 1. Lấy tất cả users có email
            List<User> allUsers = userRepository.findAll();
            List<User> usersWithEmail = allUsers.stream()
                    .filter(u -> u.getEmail() != null && !u.getEmail().trim().isEmpty())
                    .toList();

            logger.debug("Found {} users with email out of {} total users", 
                         usersWithEmail.size(), allUsers.size());

            // 2. Xác định ngày hôm nay và tuần hiện tại
            LocalDate today = LocalDate.now();
            LocalDate weekStart = today.with(DayOfWeek.MONDAY);
            int dayOfWeek = today.getDayOfWeek().getValue(); // 1=Monday, 7=Sunday

            logger.debug("Today: {}, Week starts: {}, Day of week: {}", today, weekStart, dayOfWeek);

            // 3. Với mỗi user, kiểm tra meal plan
            for (User user : usersWithEmail) {
                try {
                    // Lấy meal plan của tuần này
                    Optional<MealPlan> mealPlanOpt = mealPlanRepository
                            .findByUserIdAndWeekStartDate(user.getId(), weekStart);

                    if (mealPlanOpt.isEmpty()) {
                        continue; // User không có meal plan tuần này
                    }

                    MealPlan mealPlan = mealPlanOpt.get();

                    // Lấy tất cả items của plan
                    List<MealPlanItem> items = mealPlanItemRepository.findByMealPlan_Id(mealPlan.getId());

                    // Tìm item tương ứng với ngày hôm nay và bữa ăn hiện tại
                    Optional<MealPlanItem> itemOpt = items.stream()
                            .filter(item -> item.getDayOfWeek().equals(dayOfWeek) 
                                         && item.getMealType().equals(mealType)
                                         && item.getRecipe() != null)
                            .findFirst();

                    if (itemOpt.isPresent()) {
                        MealPlanItem item = itemOpt.get();
                        
                        // CRITICAL FIX: Force initialize recipe to avoid LazyInitializationException
                        Recipe recipe = item.getRecipe();
                        if (recipe != null) {
                            // Trigger lazy loading while we're still in transaction
                            recipe.getTitle();
                            recipe.getIngredients();
                            recipe.getInstructions();
                            recipe.getCalories();
                            recipe.getCookingTime();
                            recipe.getServings();
                            
                            // Now send email with fully loaded recipe
                            emailService.sendMealPlanNotification(user, item, mealType);
                            sentCount++;
                            logger.debug("Sent {} notification to user: {}", mealType, user.getEmail());
                        } else {
                            logger.warn("Recipe is null for MealPlanItem {}", item.getId());
                        }
                    }

                } catch (Exception e) {
                    logger.error("Error processing notification for user {}: {}", 
                                 user.getId(), e.getMessage());
                }
            }

            logger.info("✅ Completed {} notifications. Sent {} emails.", mealType, sentCount);

        } catch (Exception e) {
            logger.error("Error in {} notification scheduler: {}", mealType, e.getMessage(), e);
        }
    }
}
