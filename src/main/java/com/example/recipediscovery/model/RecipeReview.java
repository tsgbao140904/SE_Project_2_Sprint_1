package com.example.recipediscovery.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe_reviews")
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer rating; // 1-5 (review gốc), 0 (reply)

    @Lob
    @Column
    private String comment;

    /** NULL = review gốc; top-level parentId = reply */
    @Column(name = "parent_id")
    private Long parentId;

    /** User được @mention khi reply (nullable) */
    @Column(name = "mention_user_id")
    private Long mentionUserId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /** User được @mention */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mention_user_id", insertable = false, updatable = false)
    private User mentionUser;

    /** Replies (chỉ populate ở top-level, @Transient) */
    @Transient
    private List<RecipeReview> replies = new ArrayList<>();

    public RecipeReview() {}

    @PrePersist
    void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        if (this.rating == null) this.rating = 0;
    }

    // ===== HELPERS =====
    public boolean isReply()  { return this.parentId != null; }
    public boolean isEdited() { return this.updatedAt != null; }

    // ===== GETTERS / SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecipeId() { return recipeId; }
    public void setRecipeId(Long recipeId) { this.recipeId = recipeId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getMentionUserId() { return mentionUserId; }
    public void setMentionUserId(Long mentionUserId) { this.mentionUserId = mentionUserId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public User getMentionUser() { return mentionUser; }
    public void setMentionUser(User mentionUser) { this.mentionUser = mentionUser; }

    public List<RecipeReview> getReplies() { return replies; }
    public void setReplies(List<RecipeReview> replies) { this.replies = replies; }
}
