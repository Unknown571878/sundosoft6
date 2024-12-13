package edu.du.project2.repository;

import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnARepository extends JpaRepository<QnA, Long> {
    List<QnA> findAllByQnaIdOrderByIdAsc(QnAList qnaId);
}
