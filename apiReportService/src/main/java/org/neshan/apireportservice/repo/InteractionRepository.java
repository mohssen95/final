package org.neshan.apireportservice.repo;

import org.neshan.apireportservice.entity.Interaction;
import org.neshan.apireportservice.entity.Report;
import org.neshan.apireportservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {

    Interaction findByReportAndUser(Report report, User user);
}
