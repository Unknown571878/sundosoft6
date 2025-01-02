package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/noticeList")
    public String noticeList(Model model,
                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Notice> notices = noticeService.getAllNotices();
        LocalDateTime now = LocalDateTime.now();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), notices.size());
        final Page<Notice> page = new PageImpl<>(notices.subList(start, end), pageable, notices.size());
        long totalNotices = noticeService.getTotalNotices();
        model.addAttribute("notices", totalNotices);
        model.addAttribute("now", now);
        model.addAttribute("list", page); // 게시글 목록을 페이지 형식으로 모델에 추가
        return "notice/list";
    }

    // 컨트롤러 계층
    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam Long id, Model model, HttpSession session) throws Exception {
        Notice notice = noticeService.selectNoticeDetail(id,true);  // 조회수 증가 처리가 서비스에서만 발생
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        LocalDateTime now = LocalDateTime.now();
        if (authInfo != null) {
            model.addAttribute("authInfo", authInfo); // 템플릿에서 접근 가능하도록 모델에 추가
        }
        model.addAttribute("notice", notice);
        model.addAttribute("filePath", notice.getFilePaths());
        model.addAttribute("now", now);
        return "notice/detail";  // 일반 사용자 페이지
    }


}
