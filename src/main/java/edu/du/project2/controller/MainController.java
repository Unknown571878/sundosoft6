package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.Faq;
import edu.du.project2.entity.Notice;
import edu.du.project2.service.FaqService;
import edu.du.project2.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 메인 화면 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final NoticeService noticeService;
    private final FaqService faqService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // 로그인 상태 확인
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("isLoggedIn", authInfo != null);

        // 공지사항, FAQ 데이터 조회
        List<Notice> notices = noticeService.getAllNotices();
        List<Faq> faqs = faqService.getUserFAQs();

        // 각각 화면에 최대 5개 표시
        model.addAttribute("notices", notices.stream().limit(5).collect(Collectors.toList()));
        model.addAttribute("faqs", faqs.stream().limit(5).collect(Collectors.toList()));

        return "main/home";
    }
}
