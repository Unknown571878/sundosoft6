package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.dto.MessageDto;
import edu.du.project2.entity.Notice;
import edu.du.project2.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final NoticeService noticeService;


    @GetMapping("/admin")
    public String admin() {
        return "admin/adminPage";
    }


    @GetMapping("/noticeList")
    public String noticeList(Model model,
                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Notice> notices = noticeService.getAllNotices();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), notices.size());
        final Page<Notice> page = new PageImpl<>(notices.subList(start, end), pageable, notices.size());
        model.addAttribute("list", page); // 게시글 목록을 페이지 형식으로 모델에 추가
        return "notice/list";
    }

    @GetMapping("/noticeWrite")
    public String noticeWrite() {
        return "notice/write";
    }

    @PostMapping("/createNotice")
    public String createNotice(@RequestParam String title,
                               @RequestParam String content) {
        // 서비스 호출하여 공지사항 생성
        noticeService.createNotice(title, content);

        // 공지사항 작성 후 관리자 페이지로 리디렉션
        return "redirect:/noticeList";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam Long id, Model model, HttpSession session) throws Exception {
        Notice notice = noticeService.selectNoticeDetail(id);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            model.addAttribute("authInfo", authInfo); // 템플릿에서 접근 가능하도록 모델에 추가
        }
        model.addAttribute("notice", notice);
        return "notice/detail";
    }

    @PostMapping("/updateNotice")
    public String updateNotice(Notice notice) {
        noticeService.updateNotice(notice);
        return "redirect:/noticeList";
    }

    @PostMapping("/deleteNotice")
    public String deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/noticeList";
    }
}
