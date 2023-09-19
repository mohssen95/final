package org.neshan.apireportservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<org.neshan.apireportservice.entity.report.TrafficReport,Long> {
}
