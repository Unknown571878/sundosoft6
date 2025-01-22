package edu.du.project2;

import edu.du.project2.dto.MemberRequest;
import edu.du.project2.entity.*;
import edu.du.project2.repository.ApplyRepository;
import edu.du.project2.repository.DataBoardRepository;
import edu.du.project2.repository.FaqRepository;
import edu.du.project2.repository.NoticeRepository;
import edu.du.project2.service.ApplyService;
import edu.du.project2.service.DataBoardService;
import edu.du.project2.service.MemberService;
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
    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;
    private final DataBoardRepository dataBoardRepository;
    private final DataBoardService dataBoardService;
    private final ApplyService applyService;
    private final ApplyRepository applyRepository;

    public static void main(String[] args) {
        SpringApplication.run(Project2Application.class, args);
    }

    @PostConstruct
    public void init2424() {
        List<Apply> applyList = new ArrayList<>();

        // 20개의 Apply 객체 생성
        for (int i = 1; i <= 20; i++) {
            Apply apply = Apply.builder()
                    .title("제목 " + i)
                    .author("test1")
                    .content("내용 " + i + "번 분석 신청서입니다. 상세 분석을 요청합니다.")
                    .uid(3L)
                    .completedYn(i % 2 == 0 ? 'Y' : 'N') // 짝수는 완료, 홀수는 미완료
                    .request("detail")
                    .build();

            applyList.add(apply);
        }

        applyRepository.saveAll(applyList);
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
    public void init1() {
        List<Notice> notices = new ArrayList<>();

        notices.add(Notice.builder()
                .title("개인정보처리방침 안내")
                .content("본 공지는 개인정보처리방침에 대한 내용을 안내드립니다.\n\n" +
                        "1. 수집하는 개인정보 항목\n" +
                        "- 이름, 이메일 주소, 전화번호, 서비스 이용 기록 등\n\n" +
                        "2. 개인정보의 수집 및 이용 목적\n" +
                        "- 회원 관리, 서비스 제공 및 개선, 마케팅 및 광고\n\n" +
                        "3. 개인정보의 보유 및 이용 기간\n" +
                        "- 회원 탈퇴 시 지체 없이 파기하며, 관련 법령에 따라 보관이 필요한 경우 해당 기간 동안 보관\n\n" +
                        "4. 개인정보 제3자 제공\n" +
                        "- 원칙적으로 제3자에게 제공하지 않으며, 법령에 따라 필요한 경우에 한하여 제공\n\n" +
                        "5. 개인정보 보호를 위한 기술적 및 관리적 대책\n" +
                        "- 데이터 암호화, 접근 제어, 주기적인 보안 점검 등\n\n")
                .hits(0)
                .build());

        notices.add(Notice.builder()
                .title("이용약관 변경 안내")
                .content("안녕하세요, 서비스 이용약관 변경에 대한 안내드립니다.\n\n" +
                        "1. 변경 사항 주요 내용\n" +
                        "- 회원의 권리 및 의무에 대한 추가 명시\n" +
                        "- 서비스 이용 시 금지되는 행위 구체화\n" +
                        "- 서비스 제공 범위 및 회사 책임 사항 업데이트\n\n" +
                        "2. 변경 일자\n" +
                        "- 변경된 약관은 2024년 1월 1일부터 적용됩니다.\n\n" +
                        "3. 동의 여부\n" +
                        "- 본 약관에 동의하지 않는 경우, 회원 탈퇴를 요청하실 수 있으며, 계속 이용하실 경우 변경된 약관에 동의하신 것으로 간주됩니다.\n\n" +
                        "4. 문의처\n" +
                        "- 이메일: dongsundo@gmail.com\n"
                        )
                .hits(0)
                .build());

        noticeRepository.saveAll(notices);

    }

    @PostConstruct
    public void init2() {
        List<Faq> faqList = new ArrayList<>();

        faqList.add(Faq.builder()
                .id(1L)
                .title("회원가입 오류 관련")
                .question("회원가입이 진행되지 않습니다. 어떻게 해야 하나요?")
                .answer("회원가입 시 입력한 정보가 정확한지 확인해 주세요. 문제가 지속될 경우 고객센터로 문의 바랍니다.")
                .createdAt(LocalDateTime.now())
                .build());

        faqList.add(Faq.builder()
                .id(2L)
                .title("비밀번호 변경 방법")
                .question("비밀번호를 잊어버렸습니다. 어떻게 변경하나요?")
                .answer("로그인 화면에서 '비밀번호 찾기'를 클릭한 후 이메일 인증을 통해 비밀번호를 재설정하실 수 있습니다.")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build());

        faqList.add(Faq.builder()
                .id(3L)
                .title("서비스 이용 시간")
                .question("고객센터 운영 시간은 어떻게 되나요?")
                .answer("고객센터는 평일 오전 9시부터 오후 6시까지 운영됩니다. 주말 및 공휴일은 휴무입니다.")
                .createdAt(LocalDateTime.now().minusDays(5))
                .build());

        faqList.add(Faq.builder()
                .id(4L)
                .title("환불 요청 방법")
                .question("결제 후 환불 요청은 어떻게 하나요?")
                .answer("마이페이지에서 환불 요청서를 작성해 주시면 검토 후 영업일 기준 7일 이내 처리됩니다.")
                .createdAt(LocalDateTime.now().minusDays(10))
                .build());

        faqList.add(Faq.builder()
                .id(5L)
                .title("계정 삭제")
                .question("계정을 삭제하려면 어떻게 해야 하나요?")
                .answer("계정 삭제는 고객센터를 통해 요청하실 수 있습니다. 삭제 시 모든 데이터는 복구할 수 없습니다.")
                .createdAt(LocalDateTime.now().minusMonths(1))
                .build());

        faqRepository.saveAll(faqList);
    }
}
