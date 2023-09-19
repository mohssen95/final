package org.neshan.apireportservice.repo;

import org.neshan.apireportservice.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report,Long> {
}
