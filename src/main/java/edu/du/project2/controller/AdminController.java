package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.dto.MessageDto;
import edu.du.project2.entity.*;
import edu.du.project2.repository.ApplyRepository;
import edu.du.project2.repository.MemberRepository;
import edu.du.project2.repository.QnaListRepository;
import edu.du.project2.repository.QnaRepository;
import edu.du.project2.service.ApplyService;
import edu.du.project2.service.FaqService;
import edu.du.project2.service.NoticeService;
import edu.du.project2.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final NoticeService noticeService;
    private final QnaListRepository qnAListRepository;
    private final MemberRepository memberRepository;
    private final QnaRepository qnARepository;
    private final QnaService qnAService;
    private final FaqService faqService;

    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/adminPage";
    }


    @GetMapping("/admin_notice")
    public String noticeList(Model model,
                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Notice> notices = noticeService.getAllNotices();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), notices.size());
        final Page<Notice> page = new PageImpl<>(notices.subList(start, end), pageable, notices.size());
        LocalDateTime now = LocalDateTime.now();
        long totalNotices = noticeService.getTotalNotices();
        model.addAttribute("notices", totalNotices);
        model.addAttribute("now", now);
        model.addAttribute("list", page); // 게시글 목록을 페이지 형식으로 모델에 추가
        return "admin/admin_notice";
    }

    @GetMapping("/admin_qna")
    public String qnaList(Model model) {
        List<Qna> qna = qnARepository.findAll();
        List<QnaList> qnALists = qnAListRepository.findAll();
        List<Member> members = memberRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("qna", qna);
        model.addAttribute("qnaList", qnALists);
        model.addAttribute("member", members);
        return "admin/admin_qna";
    }

    @GetMapping("/admin/admin_qnaDetail")
    public String qnaDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("qna", qnAService.getInquiryDetail(id));
        model.addAttribute("lists", qnAService.getInquiryReplies(id));
        QnaList qnAList = qnAService.getInquiryDetail(id);
        Member member = memberRepository.findById(qnAList.getUid()).get();
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("member", member);
        return "/admin/admin_qnaDetail";
    }

    @PostMapping("/admin/inquiryInsert")
    public String qnaInsert(@ModelAttribute QnaList list,
                            @RequestParam String content,
                            HttpSession session) {
        qnAService.insertInquiry(list, content, session);
        return "redirect:/admin/admin_qnaDetail";
    }

    @PostMapping("/admin/answerInsert")
    public String answerInsert(@RequestParam String content,
                               @RequestParam String role,
                               @RequestParam Long id) {
        qnAService.addAnswer(content, role, id);
        return "redirect:/admin/admin_qnaDetail?id=" + id;
    }

    @PostMapping("/admin/end/{id}")
    public String end(@PathVariable Long id, Model model) {
        QnaList qnAList = qnAListRepository.findById(id).get();
        qnAList.setEndYn('Y');
        qnAListRepository.save(qnAList);
        MessageDto message = new MessageDto("문의를 종료하였습니다.", "/admin_qna", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }


    @GetMapping("/admin/admin_noticeWrite")
    public String noticeWrite(Model model) {
        return "admin/admin_noticeWrite";
    }

    @PostMapping("/admin/createNotice")
    public String createNotice(@RequestParam String title,
                               @RequestParam String content,
                               @RequestParam("files") MultipartFile[] files) throws IOException {

        // 서비스 호출하여 공지사항 생성
        noticeService.createNotice(title, content,files);

        // 공지사항 작성 후 관리자 페이지로 리디렉션
        return "redirect:/admin_notice";
    }

    @GetMapping("/admin/noticeDetail")
    public String adminNoticeDetail(@RequestParam Long id, Model model, HttpSession session) throws Exception {
        Notice notice = noticeService.selectNoticeDetail(id,false);  // 조회수 증가 처리가 서비스에서만 발생
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            model.addAttribute("authInfo", authInfo); // 템플릿에서 접근 가능하도록 모델에 추가
        }
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("notice", notice);
        return "admin/admin_noticeDetail";  // 관리자 페이지
    }

    @PostMapping("/admin/updateNotice")
    public String updateNotice(Notice notice) {
        noticeService.updateNotice(notice);
        return "redirect:/admin_notice";
    }

    @PostMapping("/admin/deleteNotice")
    public String deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/admin_notice";
    }

    @GetMapping("/admin_management")
    public String management(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/admin_management";
    }

    @PostMapping("/admin/deleteMember")
    public ResponseEntity<String> deleteMember(@RequestBody Map<String, Object> request) {
        Long memberId = Long.valueOf(request.get("id").toString());
        memberRepository.deleteById(memberId);
        return ResponseEntity.ok("삭제 성공");
    }

    @GetMapping("/admin_faq")
    public String faq(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Faq> lists = faqService.getAllFAQs(pageable);
        model.addAttribute("faqs", lists);
        return "/admin/admin_faq";
    }

    @GetMapping("/admin/admin_faqDetail")
    public String faqDetail(@RequestParam Long id, Model model) {
        Faq faq = faqService.faqDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("faq", faq);
        return "/admin/admin_faqDetail";
    }

    @PostMapping("/admin/admin_faqDelete")
    public String faqDelete(@RequestParam Long id, Model model) {
        faqService.faqDelete(id);
        MessageDto message = new MessageDto("삭제하였습니다", "/admin_faq", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
    @GetMapping("/admin/admin_faqWrite")
    public String faqWrite() {
        return "admin/admin_faqWrite";
    }

    @PostMapping("/admin/createFAQ")
    public String createFAQ(@RequestParam String title,
                            @RequestParam String question,
                            @RequestParam String answer) {
        faqService.faqCreate(title, question, answer);
        return "redirect:/admin_faq";
    }

    @PostMapping("/admin/admin_faqUpdate")
    public String updateFAQ(@RequestParam Long id,
                            @RequestParam String title,
                            @RequestParam String question,
                            @RequestParam String answer) {
        faqService.faqUpdate(id, title, question, answer);
        return "redirect:/admin_faq";
    }


//    관리자 페이지에 입지분석 신청 내역 확인 코드 추가해야함
//    신청내역 목록/신청내역 상세/신청한 입지분석 적용 후 완료된 내역을 메일로?? 어떻게 해야하지
    private final ApplyRepository applyRepository;
    private final ApplyService applyService;

    @GetMapping("/admin_apply")
    public String apply(Model model) {
        List<Apply> applies = applyRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("applies", applies);
        return "/admin/admin_apply";
    }

    @GetMapping("/admin_apply/detail")
    public String applyDetail(@RequestParam Long id, Model model) {
        Apply apply = applyService.selectApplyDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("apply", apply);
        return "/admin/admin_applyDetail";
    }
}
