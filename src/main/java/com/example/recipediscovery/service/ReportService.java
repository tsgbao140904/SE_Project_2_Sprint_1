package com.example.recipediscovery.service;

import com.example.recipediscovery.model.Report;
import com.example.recipediscovery.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepo;

    public ReportService(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
    }

    /**
     * Người dùng gửi báo cáo.
     * Ném IllegalStateException nếu đã báo cáo target này rồi.
     */
    public Report submitReport(Long reporterId, String targetType, Long targetId,
                               String reason, String description) {
        if (reportRepo.existsByReporterIdAndTargetTypeAndTargetId(reporterId, targetType, targetId)) {
            throw new IllegalStateException("Bạn đã báo cáo nội dung này rồi!");
        }
        Report r = new Report();
        r.setReporterId(reporterId);
        r.setTargetType(targetType.toUpperCase());
        r.setTargetId(targetId);
        r.setReason(reason);
        r.setDescription(description);
        r.setStatus("PENDING");
        return reportRepo.save(r);
    }

    /** Danh sách tất cả báo cáo (admin) */
    public List<Report> getAllReports() {
        return reportRepo.findAllByOrderByCreatedAtDesc();
    }

    /** Phân trang theo status (admin) */
    public Page<Report> getReportsByStatus(String status, int page, int size) {
        return reportRepo.findByStatusOrderByCreatedAtDesc(
                status, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    /** Chi tiết báo cáo */
    public Report getById(Long id) {
        return reportRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy báo cáo!"));
    }

    /** Đếm PENDING để hiện badge */
    public long countPending() {
        return reportRepo.countByStatus("PENDING");
    }

    /**
     * Admin xử lý báo cáo: RESOLVED hoặc REJECTED.
     */
    public Report processReport(Long reportId, Long adminId, String newStatus, String adminNote) {
        Report r = getById(reportId);
        r.setStatus(newStatus.toUpperCase());
        r.setAdminNote(adminNote);
        r.setProcessedBy(adminId);
        r.setProcessedAt(new Timestamp(System.currentTimeMillis()));
        return reportRepo.save(r);
    }
}
