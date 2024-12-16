package edu.du.project2.controller;

import edu.du.project2.entity.Notice;
import edu.du.project2.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final NoticeService noticeService;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "admin/adminPage";
    }

    @PostMapping("/createNotice")
    public String createNotice(@RequestParam String title,
                               @RequestParam String content) {
        // 서비스 호출하여 공지사항 생성
        noticeService.createNotice(title, content);

        // 공지사항 작성 후 관리자 페이지로 리디렉션
        return "redirect:/admin";
    }
}
