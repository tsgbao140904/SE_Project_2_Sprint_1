package com.example.recipediscovery.repository;

import com.example.recipediscovery.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    /** Tất cả báo cáo, mới nhất trước */
    List<Report> findAllByOrderByCreatedAtDesc();

    /** Lọc theo trạng thái */
    Page<Report> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);

    /** Lọc theo target */
    List<Report> findByTargetTypeAndTargetId(String targetType, Long targetId);

    /** Đếm báo cáo PENDING */
    long countByStatus(String status);

    /** Kiểm tra user đã báo cáo target chưa */
    boolean existsByReporterIdAndTargetTypeAndTargetId(
            Long reporterId, String targetType, Long targetId);
}
