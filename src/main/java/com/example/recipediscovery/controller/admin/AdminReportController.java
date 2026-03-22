package com.example.recipediscovery.controller.admin;

import com.example.recipediscovery.dto.SessionUser;
import com.example.recipediscovery.model.Report;
import com.example.recipediscovery.repository.RecipeRepository;
import com.example.recipediscovery.repository.RecipeReviewRepository;
import com.example.recipediscovery.service.ReportService;
import com.example.recipediscovery.service.RecipeReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final ReportService reportService;
    private final RecipeRepository recipeRepo;
    private final RecipeReviewRepository reviewRepo;
    private final RecipeReviewService reviewService;

    public AdminReportController(ReportService reportService,
                                 RecipeRepository recipeRepo,
                                 RecipeReviewRepository reviewRepo,
                                 RecipeReviewService reviewService) {
        this.reportService = reportService;
        this.recipeRepo    = recipeRepo;
        this.reviewRepo    = reviewRepo;
        this.reviewService = reviewService;
    }

    // =========================================================
    // DANH SÁCH BÁO CÁO
    // =========================================================
    @GetMapping
    public String list(@RequestParam(defaultValue = "PENDING") String tab,
                       @RequestParam(defaultValue = "0")       int page,
                       Model model) {

        Page<Report> reports = reportService.getReportsByStatus(tab, page, 15);
        long pendingCount    = reportService.countPending();

        model.addAttribute("reports",      reports);
        model.addAttribute("tab",          tab);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("currentPage",  page);

        return "admin/reports/list";
    }

    // =========================================================
    // CHI TIẾT BÁO CÁO
    // =========================================================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Report report = reportService.getById(id);

        // Lấy nội dung bị báo cáo để hiển thị
        if ("RECIPE".equals(report.getTargetType())) {
            recipeRepo.findById(report.getTargetId())
                    .ifPresent(r -> model.addAttribute("targetRecipe", r));
        } else if ("REVIEW".equals(report.getTargetType())) {
            reviewRepo.findById(report.getTargetId())
                    .ifPresent(r -> model.addAttribute("targetReview", r));
        }

        model.addAttribute("report", report);
        return "admin/reports/detail";
    }

    // =========================================================
    // XỬ LÝ BÁO CÁO (Duyệt / Từ chối)
    // =========================================================
    @PostMapping("/{id}/process")
    public String process(@PathVariable Long id,
                          @RequestParam String action,        // RESOLVED | REJECTED
                          @RequestParam(required = false) String adminNote,
                          @RequestParam(required = false) boolean deleteContent,
                          HttpSession session,
                          RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            Report report = reportService.getById(id);

            // Nếu admin chọn xóa nội dung vi phạm
            if (deleteContent && "RESOLVED".equalsIgnoreCase(action)) {
                if ("RECIPE".equals(report.getTargetType())) {
                    recipeRepo.deleteById(report.getTargetId());
                } else if ("REVIEW".equals(report.getTargetType())) {
                    reviewService.deleteReview(report.getTargetId(), su.getId(), true);
                }
            }

            reportService.processReport(id, su.getId(), action, adminNote);
            redirect.addFlashAttribute("saved", "Đã xử lý báo cáo!");

        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        return "redirect:/admin/reports/" + id;
    }
}
