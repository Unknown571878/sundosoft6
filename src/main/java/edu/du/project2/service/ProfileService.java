package edu.du.project2.service;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.Member;
import edu.du.project2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberRepository memberRepository;

    public void updateProfile(AuthInfo authInfo) {
        // 데이터베이스에서 사용자를 조회한 후, 데이터를 업데이트합니다.
        Member member = memberRepository.findById(authInfo.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 프로필 정보 업데이트
        member.setLoginId(authInfo.getLoginId());
        member.setEmail(authInfo.getEmail());
        member.setName(authInfo.getName());
        member.setTel(authInfo.getTel());
        member.setZipcode(authInfo.getZipcode());
        member.setAddress(authInfo.getAddress());
        member.setDetailAddress(authInfo.getDetailAddress());


        // 변경된 정보를 데이터베이스에 저장
        memberRepository.save(member);
    }

    // 데이터베이스에 해당 loginId가 존재하는지 확인
    // existsByLoginId(loginId) 메서드는 데이터베이스에 loginId가 있으면 true, 없으면 false를 반환
    // ! 연산자를 통해 결과를 반전시켜, 데이터베이스에 loginId가 없으면 true, 있으면 false 반환
    public boolean isLoginIdAvailable(String loginId) {
        return !memberRepository.existsByLoginId(loginId);
    }
}
