package com.example.recipediscovery.service;

import com.example.recipediscovery.model.RecipeReview;
import com.example.recipediscovery.repository.RecipeReviewRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeReviewService {

    private final RecipeReviewRepository reviewRepo;

    public RecipeReviewService(RecipeReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    /**
     * Lấy reviews gốc của recipe, mỗi review đính kèm danh sách replies.
     */
    public List<RecipeReview> getReviewsWithReplies(Long recipeId) {
        List<RecipeReview> topLevel = reviewRepo
                .findByRecipeIdAndParentIdIsNullOrderByCreatedAtDesc(recipeId);

        for (RecipeReview rev : topLevel) {
            List<RecipeReview> replies = reviewRepo.findByParentIdOrderByCreatedAtAsc(rev.getId());
            rev.setReplies(replies);
        }
        return topLevel;
    }

    /** Điểm trung bình (null nếu chưa có review nào) */
    public Double getAverageRating(Long recipeId) {
        return reviewRepo.findAverageRatingByRecipeId(recipeId);
    }

    /** Tổng số reviews gốc */
    public long countReviews(Long recipeId) {
        return reviewRepo.countByRecipeIdAndParentIdIsNull(recipeId);
    }

    /** Review gốc của user hiện tại (Optional) */
    public Optional<RecipeReview> getUserReview(Long recipeId, Long userId) {
        return reviewRepo.findByRecipeIdAndUserIdAndParentIdIsNull(recipeId, userId);
    }

    /**
     * Gửi đánh giá gốc.
     * Ném IllegalStateException nếu user đã review rồi.
     */
    public RecipeReview addReview(Long recipeId, Long userId, int rating, String comment) {
        if (reviewRepo.findByRecipeIdAndUserIdAndParentIdIsNull(recipeId, userId).isPresent()) {
            throw new IllegalStateException("Bạn đã đánh giá công thức này rồi!");
        }
        RecipeReview review = new RecipeReview();
        review.setRecipeId(recipeId);
        review.setUserId(userId);
        review.setRating(Math.max(1, Math.min(5, rating)));
        review.setComment(comment != null ? comment.trim() : "");
        review.setParentId(null);
        return reviewRepo.save(review);
    }

    /**
     * Gửi reply cho một comment.
     * parentId phải là review gốc hợp lệ.
     */
    public RecipeReview addReply(Long recipeId, Long parentId, Long userId, String comment) {
        RecipeReview parent = reviewRepo.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy comment!"));
        if (!parent.getRecipeId().equals(recipeId)) {
            throw new IllegalArgumentException("Comment không thuộc recipe này!");
        }
        // Nếu reply của reply → gắn vào top-level root để hiển thị phẳng
        Long rootParentId = (parent.getParentId() != null) ? parent.getParentId() : parentId;

        RecipeReview reply = new RecipeReview();
        reply.setRecipeId(recipeId);
        reply.setUserId(userId);
        reply.setRating(0);
        reply.setComment(comment != null ? comment.trim() : "");
        reply.setParentId(rootParentId);
        reply.setMentionUserId(parent.getUserId()); // @mention người được reply
        return reviewRepo.save(reply);
    }

    /**
     * Chỉnh sửa comment/reply của chính mình.
     */
    public RecipeReview editReview(Long reviewId, Long requesterId, String newComment, Integer newRating) {
        RecipeReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đánh giá!"));

        if (!review.getUserId().equals(requesterId)) {
            throw new SecurityException("Bạn không có quyền sửa đánh giá này!");
        }

        review.setComment(newComment != null ? newComment.trim() : review.getComment());

        // Chỉ update rating nếu là review gốc và có rating mới
        if (review.getParentId() == null && newRating != null && newRating >= 1 && newRating <= 5) {
            review.setRating(newRating);
        }
        review.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return reviewRepo.save(review);
    }

    /**
     * Xóa review hoặc reply.
     * User chỉ xóa được của mình (trừ khi isAdmin = true).
     */
    public void deleteReview(Long reviewId, Long requesterId, boolean isAdmin) {
        RecipeReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đánh giá!"));

        if (!isAdmin && !review.getUserId().equals(requesterId)) {
            throw new SecurityException("Bạn không có quyền xóa đánh giá này!");
        }
        reviewRepo.deleteById(reviewId);
    }
}
