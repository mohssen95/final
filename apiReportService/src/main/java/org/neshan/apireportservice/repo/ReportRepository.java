package org.neshan.apireportservice.repo;

import org.neshan.apireportservice.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}