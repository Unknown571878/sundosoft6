package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.FaQ;
import edu.du.project2.entity.Notice;
import edu.du.project2.service.FAQService;
import edu.du.project2.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final NoticeService noticeService;
    private final FAQService faqService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            model.addAttribute("isLoggedIn", true);  // 로그인된 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 로그인되지 않은 상태
        }
        List<Notice> notices = noticeService.getAllNotices();
        List<FaQ> faqs = faqService.getUserFAQs();

        // 공지사항 최대 5개로 제한
        List<Notice> limitedNotices = notices.stream()
                .limit(5)
                .collect(Collectors.toList());
        // faq 최대 5개로 제한
        List<FaQ> limitedFaqs = faqs.stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("faqs", limitedFaqs);
        model.addAttribute("notices", limitedNotices);
        return "main/home";
    }

}
