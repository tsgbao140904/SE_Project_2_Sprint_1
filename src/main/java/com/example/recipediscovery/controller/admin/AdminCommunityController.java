package com.example.recipediscovery.controller.admin;

import com.example.recipediscovery.model.Recipe;
import com.example.recipediscovery.model.RecipeReview;
import com.example.recipediscovery.repository.RecipeRepository;
import com.example.recipediscovery.service.RecipeReviewService;
import com.example.recipediscovery.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/admin/community")
public class AdminCommunityController {

    private final RecipeRepository recipeRepo;
    private final RecipeService recipeService;
    private final RecipeReviewService reviewService;

    public AdminCommunityController(RecipeRepository recipeRepo,
                                    RecipeService recipeService,
                                    RecipeReviewService reviewService) {
        this.recipeRepo    = recipeRepo;
        this.recipeService = recipeService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "pending") String tab, Model model) {

        List<Recipe> data = switch (tab) {
            case "approved" -> recipeRepo.findByShareStatus("APPROVED");
            case "rejected" -> recipeRepo.findByShareStatus("REJECTED");
            default         -> recipeRepo.findByShareStatus("PENDING");
        };

        model.addAttribute("recipes", data);
        model.addAttribute("tab", tab);

        return "admin/community/list";
    }

    @GetMapping("/{id}")
    public String viewDetail(@PathVariable Long id, Model model) {

        Recipe r = recipeService.getById(id);

        // Thêm dữ liệu reviews cho admin xem
        List<RecipeReview> reviews   = reviewService.getReviewsWithReplies(id);
        Double             avgRating = reviewService.getAverageRating(id);
        long          totalReviews   = reviewService.countReviews(id);

        model.addAttribute("recipe",       r);
        model.addAttribute("reviews",      reviews);
        model.addAttribute("avgRating",    avgRating);
        model.addAttribute("totalReviews", totalReviews);

        return "admin/community/detail";
    }

    @PostMapping("/{id}/update")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam(required = false) String reason,
                               RedirectAttributes redirect) {

        Recipe r = recipeService.getById(id);

        r.setShareStatus(status);

        if ("REJECTED".equals(status)) {
            r.setShareRejectedReason(reason);
            r.setShareApprovedAt(null);
        } else {
            r.setShareRejectedReason(null);
        }

        if ("APPROVED".equals(status)) {
            r.setShareApprovedAt(new Timestamp(System.currentTimeMillis()));
        }

        recipeRepo.save(r);
        redirect.addFlashAttribute("saved", true);

        return "redirect:/admin/community/" + id;
    }

    // =========================================================
    // ADMIN XÓA BẤT KỲ REVIEW
    // =========================================================
    @PostMapping("/{id}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long id,
                               @PathVariable Long reviewId,
                               RedirectAttributes redirect) {
        try {
            reviewService.deleteReview(reviewId, null, true);
            redirect.addFlashAttribute("saved", "Đã xóa bình luận.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể xóa bình luận.");
        }
        return "redirect:/admin/community/" + id;
    }
}
