package org.neshan.apireportservice.repo;

import org.neshan.apireportservice.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "select dt from (\n" +
            "    select date_trunc('hour', timestamp) as dt, count(*) from report\n" +
            "    where date_trunc('day', timestamp) = :date and report_type='ACCIDENT'\n" +
            "    group by date_trunc('hour', timestamp)\n" +
            "    order by count(id) desc\n" +
            "    ) as b\n" +
            "limit 1",nativeQuery = true)

    Timestamp getMostAccidents(@Param("date") Timestamp date);
}
