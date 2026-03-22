package com.example.recipediscovery.controller.user;

import com.example.recipediscovery.dto.SessionUser;
import com.example.recipediscovery.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * POST /app/community/{id}/report  → báo cáo công thức
     */
    @PostMapping("/app/community/{id}/report")
    public String reportRecipe(@PathVariable Long id,
                               @RequestParam String reason,
                               @RequestParam(required = false) String description,
                               HttpSession session,
                               RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reportService.submitReport(su.getId(), "RECIPE", id, reason, description);
            redirect.addFlashAttribute("reportSuccess", "Báo cáo đã được gửi. Cảm ơn bạn!");
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("reportError", e.getMessage());
        }
        return "redirect:/app/community/" + id;
    }

    /**
     * POST /app/community/{id}/reviews/{reviewId}/report  → báo cáo bình luận
     */
    @PostMapping("/app/community/{id}/reviews/{reviewId}/report")
    public String reportReview(@PathVariable Long id,
                               @PathVariable Long reviewId,
                               @RequestParam String reason,
                               @RequestParam(required = false) String description,
                               HttpSession session,
                               RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reportService.submitReport(su.getId(), "REVIEW", reviewId, reason, description);
            redirect.addFlashAttribute("reportSuccess", "Báo cáo bình luận đã được gửi!");
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("reportError", e.getMessage());
        }
        return "redirect:/app/community/" + id;
    }
}
