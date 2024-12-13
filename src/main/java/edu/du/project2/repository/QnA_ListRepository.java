package edu.du.project2.repository;

import edu.du.project2.entity.QnAList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnA_ListRepository extends JpaRepository<QnAList, Long> {
    public List<QnAList> findAllByUidOrderByIdDesc(Long uid);
    public List<QnAList> findAllByOrderByIdDesc();
}
