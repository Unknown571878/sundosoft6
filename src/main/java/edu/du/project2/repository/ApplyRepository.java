package edu.du.project2.repository;

import edu.du.project2.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("SELECT a FROM Apply a WHERE a.id = :id")
    Apply selectApplyDetail(Long id); // ID로 특정 게시물 정보를 반환

}
