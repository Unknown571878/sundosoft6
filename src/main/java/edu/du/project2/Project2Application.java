package edu.du.project2;

import edu.du.project2.dto.MemberRequest;
import edu.du.project2.entity.Notice;
import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import edu.du.project2.service.MemberService;
import edu.du.project2.service.NoticeService;
import edu.du.project2.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@SpringBootApplication
public class Project2Application {

    private final MemberService memberService;
    private final NoticeService noticeService;
    private final QnAService qnAService;
    public static void main(String[] args) {
        SpringApplication.run(Project2Application.class, args);
    }

    @PostConstruct
    public void init() {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setTel("");
        memberRequest.setEmail("123@123");
        memberRequest.setPassword("123");
        memberRequest.setName("test1");
        memberRequest.setAddress("");
        memberRequest.setDetailAddress("");
        memberService.registerMember(memberRequest);

        Notice notice = new Notice();
        notice.setContent("오늘 하루가 길다.");
        notice.setTitle("오늘 하루가 길다");
        noticeService.createNotice(notice.getTitle(), notice.getContent());
    }
}
