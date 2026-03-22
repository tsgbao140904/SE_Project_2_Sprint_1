package com.example.recipediscovery.controller.admin;

import com.example.recipediscovery.repository.*;
import com.example.recipediscovery.service.ReportService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;

@Controller
public class AdminController {

    private final UserRepository       userRepo;
    private final RecipeRepository     recipeRepo;
    private final RecipeReviewRepository reviewRepo;
    private final MealPlanRepository   mealPlanRepo;
    private final ReportService        reportService;
    private final JdbcTemplate         jdbc;

    public AdminController(UserRepository userRepo,
                           RecipeRepository recipeRepo,
                           RecipeReviewRepository reviewRepo,
                           MealPlanRepository mealPlanRepo,
                           ReportService reportService,
                           JdbcTemplate jdbc) {
        this.userRepo     = userRepo;
        this.recipeRepo   = recipeRepo;
        this.reviewRepo   = reviewRepo;
        this.mealPlanRepo = mealPlanRepo;
        this.reportService = reportService;
        this.jdbc          = jdbc;
    }

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {

        // ── 1. Stats cards ──────────────────────────────────────────────
        model.addAttribute("totalUsers",    userRepo.count());
        model.addAttribute("totalRecipes",  recipeRepo.count());
        model.addAttribute("totalMealPlans",mealPlanRepo.count());
        model.addAttribute("totalReviews",  reviewRepo.count());
        model.addAttribute("pending",       recipeRepo.countByShareStatus("PENDING"));
        model.addAttribute("approved",      recipeRepo.countByShareStatus("APPROVED"));
        model.addAttribute("rejected",      recipeRepo.countByShareStatus("REJECTED"));
        model.addAttribute("pendingReports",reportService.countPending());

        // ── 2. Monthly growth – last 6 months ───────────────────────────
        List<String> months      = new ArrayList<>();
        List<Long>   newUsers    = new ArrayList<>();
        List<Long>   newRecipes  = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for (int i = 5; i >= 0; i--) {
            LocalDate start = today.minusMonths(i).withDayOfMonth(1);
            LocalDate end   = start.plusMonths(1);
            months.add(start.getMonth().getDisplayName(
                    java.time.format.TextStyle.SHORT, new Locale("vi")));

            Long uc = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE created_at >= ? AND created_at < ?",
                Long.class, java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));
            Long rc = jdbc.queryForObject(
                "SELECT COUNT(*) FROM recipes WHERE created_at >= ? AND created_at < ?",
                Long.class, java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));

            newUsers.add(uc == null ? 0 : uc);
            newRecipes.add(rc == null ? 0 : rc);
        }
        model.addAttribute("months",     months);
        model.addAttribute("newUsers",   newUsers);
        model.addAttribute("newRecipes", newRecipes);

        // ── 3. Top 5 công thức được yêu thích nhất ──────────────────────
        List<Map<String, Object>> topRecipes = jdbc.queryForList("""
            SELECT r.id, r.title, r.image_url,
                   COUNT(DISTINCT f.id)           AS fav_count,
                   COUNT(DISTINCT rv.id)          AS review_count,
                   COALESCE(AVG(CASE WHEN rv.parent_id IS NULL AND rv.rating > 0
                               THEN rv.rating END), 0) AS avg_rating
            FROM recipes r
            LEFT JOIN favorites     f  ON f.recipe_id = r.id
            LEFT JOIN recipe_reviews rv ON rv.recipe_id = r.id
            WHERE r.share_status = 'APPROVED'
            GROUP BY r.id, r.title, r.image_url
            ORDER BY fav_count DESC, avg_rating DESC
            LIMIT 5
            """);
        model.addAttribute("topRecipes", topRecipes);

        // ── 4. Người dùng mới nhất (5 người) ────────────────────────────
        model.addAttribute("recentUsers",
            userRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 5)));

        model.addAttribute("activePage", "dashboard");
        return "admin/dashboard";
    }
}
