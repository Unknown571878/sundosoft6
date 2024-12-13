package edu.du.project2;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.Member;
import edu.du.project2.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Project2ApplicationTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void admin_member_초기값() {
        Member admin = Member.builder()
                .name("admin")
                .email("admin2@admin.com")
                .password(passwordEncoder.encode("1234"))
                .role("ADMIN")
                .tel("01000000000")
                .build();

        memberRepository.save(admin);
//        AuthInfo authInfo = new AuthInfo(1L, "admin@test.com", "관리자", "admin");
        Member member = Member.builder()
                .name("member")
                .email("member@member.com")
                .password(passwordEncoder.encode("1234"))
                .role("USER")
                .tel("01011111111")
                .build();

        memberRepository.save(member);
    }

    @Test
    void test(){

    }

}
