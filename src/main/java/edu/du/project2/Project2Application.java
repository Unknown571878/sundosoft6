package edu.du.project2;

import edu.du.project2.dto.MemberRequest;
import edu.du.project2.entity.Notice;
import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import edu.du.project2.entity.FaQ;
import edu.du.project2.repository.FAQRepository;
import edu.du.project2.service.MemberService;
import edu.du.project2.service.NoticeService;
import edu.du.project2.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Project2Application {

    private final MemberService memberService;
    private final NoticeService noticeService;
    private final QnAService qnAService;
    private final FAQRepository faqRepository;
    public static void main(String[] args) {
        SpringApplication.run(Project2Application.class, args);
    }

    @PostConstruct
    public void init() {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setTel("");
        memberRequest.setLoginId("123");
        memberRequest.setEmail("123@123");
        memberRequest.setPassword("123");
        memberRequest.setName("test1");
        memberRequest.setZipcode("123");
        memberRequest.setAddress("123");
        memberRequest.setDetailAddress("123");
        memberService.registerMember(memberRequest);
    }

    @PostConstruct
    public void init2() {
        List<FaQ> faqList = new ArrayList<>();

        faqList.add(FaQ.builder()
                .id(1L)
                .title("회원가입 오류 관련")
                .Question("회원가입이 진행되지 않습니다. 어떻게 해야 하나요?")
                .Answer("회원가입 시 입력한 정보가 정확한지 확인해 주세요. 문제가 지속될 경우 고객센터로 문의 바랍니다.")
                .deletedYn("N")
                .createdAt(LocalDateTime.now())
                .build());

        faqList.add(FaQ.builder()
                .id(2L)
                .title("비밀번호 변경 방법")
                .Question("비밀번호를 잊어버렸습니다. 어떻게 변경하나요?")
                .Answer("로그인 화면에서 '비밀번호 찾기'를 클릭한 후 이메일 인증을 통해 비밀번호를 재설정하실 수 있습니다.")
                .deletedYn("N")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build());

        faqList.add(FaQ.builder()
                .id(3L)
                .title("서비스 이용 시간")
                .Question("고객센터 운영 시간은 어떻게 되나요?")
                .Answer("고객센터는 평일 오전 9시부터 오후 6시까지 운영됩니다. 주말 및 공휴일은 휴무입니다.")
                .deletedYn("N")
                .createdAt(LocalDateTime.now().minusDays(5))
                .build());

        faqList.add(FaQ.builder()
                .id(4L)
                .title("환불 요청 방법")
                .Question("결제 후 환불 요청은 어떻게 하나요?")
                .Answer("마이페이지에서 환불 요청서를 작성해 주시면 검토 후 영업일 기준 7일 이내 처리됩니다.")
                .deletedYn("N")
                .createdAt(LocalDateTime.now().minusDays(10))
                .build());

        faqList.add(FaQ.builder()
                .id(5L)
                .title("계정 삭제")
                .Question("계정을 삭제하려면 어떻게 해야 하나요?")
                .Answer("계정 삭제는 고객센터를 통해 요청하실 수 있습니다. 삭제 시 모든 데이터는 복구할 수 없습니다.")
                .deletedYn("N")
                .createdAt(LocalDateTime.now().minusMonths(1))
                .build());

        faqRepository.saveAll(faqList);
    }
}
