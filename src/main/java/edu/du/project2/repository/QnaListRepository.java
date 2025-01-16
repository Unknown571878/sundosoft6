package edu.du.project2.repository;

import edu.du.project2.entity.QnaList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaListRepository extends JpaRepository<QnaList, Long> {
    public List<QnaList> findAllByUidOrderByIdDesc(Long uid);
    public List<QnaList> findAllByOrderByIdDesc();
    public List<QnaList> findAllByStateOrderByIdAsc(char state);
}
