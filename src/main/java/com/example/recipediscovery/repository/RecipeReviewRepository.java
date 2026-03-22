package com.example.recipediscovery.repository;

import com.example.recipediscovery.model.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {

    /** Chỉ lấy review gốc (parent_id IS NULL), mới nhất trước */
    List<RecipeReview> findByRecipeIdAndParentIdIsNullOrderByCreatedAtDesc(Long recipeId);

    /** Lấy tất cả replies của một comment (parent_id = parentId) */
    List<RecipeReview> findByParentIdOrderByCreatedAtAsc(Long parentId);

    /** Tìm review gốc của 1 user trên 1 recipe */
    Optional<RecipeReview> findByRecipeIdAndUserIdAndParentIdIsNull(Long recipeId, Long userId);

    /** AVG rating chỉ tính trên review gốc */
    @Query("SELECT AVG(r.rating) FROM RecipeReview r WHERE r.recipeId = :recipeId AND r.parentId IS NULL AND r.rating > 0")
    Double findAverageRatingByRecipeId(@Param("recipeId") Long recipeId);

    /** Đếm số review gốc */
    long countByRecipeIdAndParentIdIsNull(Long recipeId);
}
