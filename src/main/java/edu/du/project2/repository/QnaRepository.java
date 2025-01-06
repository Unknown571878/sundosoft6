package edu.du.project2.repository;

import edu.du.project2.entity.Qna;
import edu.du.project2.entity.QnaList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findAllByQnaIdOrderByIdAsc(QnaList qnaId);
}
