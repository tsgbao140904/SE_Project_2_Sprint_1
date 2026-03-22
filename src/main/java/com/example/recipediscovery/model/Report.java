package com.example.recipediscovery.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    /** "RECIPE" hoặc "REVIEW" */
    @Column(name = "target_type", nullable = false)
    private String targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private String reason;

    @Lob
    @Column
    private String description;

    /** PENDING / RESOLVED / REJECTED */
    @Column(nullable = false)
    private String status = "PENDING";

    @Lob
    @Column(name = "admin_note")
    private String adminNote;

    @Column(name = "processed_by")
    private Long processedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "processed_at")
    private Timestamp processedAt;

    // --- Eager joins để hiển thị ngay ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", insertable = false, updatable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "processed_by", insertable = false, updatable = false)
    private User processor;

    public Report() {}

    @PrePersist
    void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        if (this.status == null) this.status = "PENDING";
    }

    // ===== HELPERS =====
    public boolean isPending()  { return "PENDING".equals(status); }
    public boolean isResolved() { return "RESOLVED".equals(status); }
    public boolean isRejected() { return "REJECTED".equals(status); }

    // ===== GETTERS / SETTERS =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getReporterId() { return reporterId; }
    public void setReporterId(Long reporterId) { this.reporterId = reporterId; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }

    public Long getProcessedBy() { return processedBy; }
    public void setProcessedBy(Long processedBy) { this.processedBy = processedBy; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getProcessedAt() { return processedAt; }
    public void setProcessedAt(Timestamp processedAt) { this.processedAt = processedAt; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public User getProcessor() { return processor; }
    public void setProcessor(User processor) { this.processor = processor; }
}
