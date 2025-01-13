package edu.du.project2.repository;

import edu.du.project2.entity.KioskBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KioskBaseRepository extends JpaRepository<KioskBase, Long> {
    List<KioskBase> findAllByName(String name);
}
