package edu.du.project2.service;


import edu.du.project2.config.SecurityConfig;
import edu.du.project2.dto.LoginDto;
import edu.du.project2.dto.MemberRequest;
import edu.du.project2.entity.Member;
import edu.du.project2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdminAccount() {
                Member admin = new Member();
                admin.setLoginId("admin");
                admin.setEmail("admin@admin.com");  // 관리자 이메일
                admin.setPassword(passwordEncoder.encode("admin123"));  // 관리자 비밀번호 (암호화)
                admin.setName("관리자");  // 관리자 이름
                admin.setTel("000-0000-0000");
                admin.setRole("ADMIN");  // 관리자 역할

                // 관리자 정보를 저장
                memberRepository.save(admin);
    }

    @PostConstruct
    public void createUserAccount() {
        Member user = new Member();
        user.setLoginId("user");
        user.setEmail("user@user.com");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setName("유저");
        user.setTel("000-0000-0000");
        user.setRole("USER");

        // 관리자 정보를 저장
        memberRepository.save(user);
    }

    public boolean registerCheckId(String loginId){
        log.info("admin을 넣음 {}", loginId);
        log.info("admin을 넣으면 false가 나와야 함 {}", memberRepository.findByLoginId(loginId));
        return memberRepository.findByLoginId(loginId).isEmpty();
    }

    public String registerMember(MemberRequest request) {
        Member member = new Member();
        member.setLoginId(request.getLoginId());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword())); // 비밀번호 암호화
        member.setName(request.getName());
        member.setTel(request.getTel());
        member.setZipcode(request.getZipcode());
        member.setAddress(request.getAddress());
        member.setDetailAddress(request.getDetailAddress());
        member.setRole("USER");

        memberRepository.save(member);
        return "회원가입 성공";
    }

    public boolean loginMember(LoginDto dto) {
        Member member = memberRepository.findByLoginId(dto.getLoginId()).orElse(null);
        log.info("로그인계정 정보: {}", member);
        if (member != null && passwordEncoder.matches(dto.getPassword(), member.getPassword())){ // 비밀번호 암호화 필요
            return true;
        }
        return false;
    }

    public String findLoginIdByNameAndEmail(String name, String email) {
        return memberRepository.findLoginIdByNameAndEmail(name, email).orElse(null);
    }
}
