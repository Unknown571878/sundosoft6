package edu.du.project2.repository;


import edu.du.project2.entity.DataBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBoardRepository extends JpaRepository<DataBoard,Long> {
}
