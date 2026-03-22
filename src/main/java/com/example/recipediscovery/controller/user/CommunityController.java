package com.example.recipediscovery.controller.user;

import com.example.recipediscovery.dto.SessionUser;
import com.example.recipediscovery.model.Recipe;
import com.example.recipediscovery.model.RecipeReview;
import com.example.recipediscovery.model.User;
import com.example.recipediscovery.repository.RecipeRepository;
import com.example.recipediscovery.repository.UserRepository;
import com.example.recipediscovery.service.RecipeReviewService;
import com.example.recipediscovery.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CommunityController {

    private final RecipeRepository recipeRepo;
    private final RecipeService recipeService;
    private final RecipeReviewService reviewService;
    private final UserRepository userRepo;

    public CommunityController(RecipeRepository recipeRepo,
                               RecipeService recipeService,
                               RecipeReviewService reviewService,
                               UserRepository userRepo) {
        this.recipeRepo    = recipeRepo;
        this.recipeService = recipeService;
        this.reviewService = reviewService;
        this.userRepo      = userRepo;
    }

    @GetMapping("/app/community")
    public String community(@RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {

        // 1) Lấy toàn bộ dữ liệu (all)
        List<Recipe> all;
        if (q != null && !q.trim().isEmpty()) {
            all = recipeService.searchCommunityRecipes(q.trim());
        } else {
            all = recipeRepo.findByShareStatusAndIsPublic("APPROVED", 1);
        }

        // 2) Phân trang an toàn
        int pageSize     = 12;
        int totalRecipes = all.size();
        int totalPages   = (int) Math.ceil(totalRecipes / (double) pageSize);
        if (totalPages < 1) totalPages = 1;
        if (page < 0) page = 0;
        if (page > totalPages - 1) page = totalPages - 1;

        int start = page * pageSize;
        int end   = Math.min(start + pageSize, totalRecipes);

        List<Recipe> currentRecipes = (totalRecipes == 0) ? List.of() : all.subList(start, end);

        model.addAttribute("recipes",       currentRecipes);
        model.addAttribute("keyword",       q);
        model.addAttribute("pageNum",       page);
        model.addAttribute("totalPages",    totalPages);
        model.addAttribute("totalRecipes",  totalRecipes);

        return "user/community-home";
    }

    // =========================================================
    // CHI TIẾT CÔNG THỨC TRONG COMMUNITY (có reviews)
    // =========================================================
    @GetMapping("/app/community/{id}")
    public String communityDetail(@PathVariable Long id,
                                  HttpSession session,
                                  Model model) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        Recipe r = recipeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy"));

        if (!"APPROVED".equals(r.getShareStatus())) {
            return "redirect:/app/community";
        }

        boolean isOwner = r.getUserId().equals(su.getId());

        // --- Lấy thông tin tác giả ---
        User recipeAuthor = userRepo.findById(r.getUserId()).orElse(null);

        // --- Dữ liệu reviews (có kèm replies) ---
        List<RecipeReview>     reviews      = reviewService.getReviewsWithReplies(id);
        Double                 avgRating    = reviewService.getAverageRating(id);
        long                   totalReviews = reviewService.countReviews(id);
        Optional<RecipeReview> userReview   = reviewService.getUserReview(id, su.getId());

        model.addAttribute("recipe",        r);
        model.addAttribute("isOwner",       isOwner);
        model.addAttribute("isCommunity",   true);
        model.addAttribute("recipeAuthor",  recipeAuthor);
        model.addAttribute("reviews",       reviews);
        model.addAttribute("avgRating",     avgRating);
        model.addAttribute("totalReviews",  totalReviews);
        model.addAttribute("userReview",    userReview.orElse(null));
        model.addAttribute("currentUserId", su.getId());

        return "user/recipe-detail";
    }

    // =========================================================
    // GỬI ĐÁNH GIÁ GỐC
    // =========================================================
    @PostMapping("/app/community/{id}/reviews")
    public String submitReview(@PathVariable Long id,
                               @RequestParam int rating,
                               @RequestParam(required = false) String comment,
                               HttpSession session,
                               RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reviewService.addReview(id, su.getId(), rating, comment);
            redirect.addFlashAttribute("reviewSuccess", "Cảm ơn bạn đã đánh giá!");
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("reviewError", e.getMessage());
        }
        return "redirect:/app/community/" + id;
    }

    // =========================================================
    // REPLY MỘT COMMENT
    // =========================================================
    @PostMapping("/app/community/{id}/reviews/{parentId}/reply")
    public String replyReview(@PathVariable Long id,
                              @PathVariable Long parentId,
                              @RequestParam String comment,
                              HttpSession session,
                              RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reviewService.addReply(id, parentId, su.getId(), comment);
            redirect.addFlashAttribute("reviewSuccess", "Đã gửi phản hồi!");
        } catch (Exception e) {
            redirect.addFlashAttribute("reviewError", "Không thể gửi phản hồi: " + e.getMessage());
        }
        return "redirect:/app/community/" + id + "#review-" + parentId;
    }

    // =========================================================
    // CHỈNH SỬA COMMENT / REPLY
    // =========================================================
    @PostMapping("/app/community/{id}/reviews/{reviewId}/edit")
    public String editReview(@PathVariable Long id,
                             @PathVariable Long reviewId,
                             @RequestParam(required = false) String comment,
                             @RequestParam(required = false) Integer rating,
                             HttpSession session,
                             RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reviewService.editReview(reviewId, su.getId(), comment, rating);
            redirect.addFlashAttribute("reviewSuccess", "Đã cập nhật đánh giá!");
        } catch (Exception e) {
            redirect.addFlashAttribute("reviewError", "Không thể sửa: " + e.getMessage());
        }
        return "redirect:/app/community/" + id;
    }

    // =========================================================
    // XÓA REVIEW / REPLY
    // =========================================================
    @PostMapping("/app/community/{id}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long id,
                               @PathVariable Long reviewId,
                               HttpSession session,
                               RedirectAttributes redirect) {

        SessionUser su = (SessionUser) session.getAttribute("USER");
        if (su == null) return "redirect:/login";

        try {
            reviewService.deleteReview(reviewId, su.getId(), false);
            redirect.addFlashAttribute("reviewSuccess", "Đã xóa.");
        } catch (Exception e) {
            redirect.addFlashAttribute("reviewError", "Không thể xóa.");
        }
        return "redirect:/app/community/" + id;
    }
}
