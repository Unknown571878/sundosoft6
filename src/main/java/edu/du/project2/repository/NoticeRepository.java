package edu.du.project2.repository;


import edu.du.project2.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 특정 게시물의 상세 정보를 조회하는 JPQL 쿼리
    @Query("SELECT n FROM Notice n WHERE n.id = :id")
    Notice selectNoticeDetail(Long id); // ID로 특정 게시물 정보를 반환

    // 조회수(hit)를 1 증가시키는 JPQL 쿼리
    @Modifying
    @Query("UPDATE Notice n set n.hits = n.hits + 1 where n.id = :id")
    void updateHitCount(Long id); // ID를 기준으로 조회수를 증가시킴

}
