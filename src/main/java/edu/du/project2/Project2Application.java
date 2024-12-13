package edu.du.project2;

import edu.du.project2.dto.MemberRequest;
import edu.du.project2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
@RequiredArgsConstructor
@SpringBootApplication
public class Project2Application {

    private final MemberService memberService;
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

    }


}
