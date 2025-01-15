package edu.du.project2.repository;

import edu.du.project2.entity.Apply;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("SELECT a FROM Apply a WHERE a.id = :id")
    Apply selectApplyDetail(Long id); // ID로 특정 게시물 정보를 반환

    // 전체 신청서 목록 (관리자 전용)
    List<Apply> findAll(Sort sort);

    // 특정 사용자 신청서 목록
    List<Apply> findAllByUidOrderByCreatedAtDesc(Long uid);

}
