package edu.du.project2.repository;


import edu.du.project2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    @Query("select m.loginId from Member m where m.name = :name and m.email = :email")
    Optional<String> findLoginIdByNameAndEmail(String name, String email);

    boolean existsByLoginId(String loginId);
}
