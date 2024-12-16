package edu.du.project2.repository;

import edu.du.project2.entity.FaQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FaQ, Long> {
}
