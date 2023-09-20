package org.neshan.apireportservice.repo;

import org.neshan.apireportservice.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {
}
