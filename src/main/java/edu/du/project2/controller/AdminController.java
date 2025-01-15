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
import edu.du.project2.utils.PagingUtils;
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

/**
 * 관리자 페이지 요청을 처리하는 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final NoticeService noticeService;
    private final QnaListRepository qnAListRepository;
    private final MemberRepository memberRepository;
    private final QnaRepository qnARepository;
    private final QnaService qnaService;
    private final FaqService faqService;
    private final ApplyRepository applyRepository;
    private final ApplyService applyService;

    // 메시지 출력 및 리다이렉트를 처리하는 공통 메서드.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }
    // 현재 시간을 반환하는 공통 메서드.
    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    // -------------------------------------------
    // 관리자 페이지 메인
    // -------------------------------------------

    @GetMapping("/admin")
    public String admin() {
        return "admin/adminPage";
    }

    // -------------------------------------------
    // 공지사항 관리
    // -------------------------------------------

    @GetMapping("/admin/admin_notice")
    public String noticeList(Model model,
                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Notice> notices = noticeService.getAllNotices();
        Page<Notice> page = PagingUtils.createPage(notices, pageable);
        model.addAttribute("notices", notices.size());
        model.addAttribute("now", getCurrentTime());
        model.addAttribute("list", page);
        return "admin/admin_notice";
    }

    @GetMapping("/admin/admin_noticeWrite")
    public String noticeWrite() {
        return "admin/admin_noticeWrite";
    }

    @PostMapping("/admin/createNotice")
    public String createNotice(@RequestParam String title,
                               @RequestParam String content,
                               @RequestParam("files") MultipartFile[] files) throws IOException {
        noticeService.createNotice(title, content,files); // 서비스 호출하여 공지사항 생성
        return "redirect:/admin/admin_notice";
    }

    @GetMapping("/admin/noticeDetail")
    public String adminNoticeDetail(@RequestParam Long id, Model model, HttpSession session) throws Exception {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            model.addAttribute("authInfo", authInfo); // 템플릿에서 접근 가능하도록 모델에 추가
        }
        model.addAttribute("now", getCurrentTime());
        model.addAttribute("notice", noticeService.getNoticeDetail(id,false));
        return "admin/admin_noticeDetail";  // 관리자 페이지
    }

    @PostMapping("/admin/updateNotice")
    public String updateNotice(Notice notice) {
        noticeService.updateNotice(notice);
        return "redirect:/admin/admin_notice";
    }

    @PostMapping("/admin/deleteNotice")
    public String deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/admin/admin_notice";
    }

    // -------------------------------------------
    // Q&A 관리
    // -------------------------------------------

    @GetMapping("/admin/admin_qna")
    public String qnaList(Model model) {
        model.addAttribute("now", getCurrentTime());
        model.addAttribute("qna", qnARepository.findAll());
        model.addAttribute("qnaList", qnAListRepository.findAll());
        model.addAttribute("member", memberRepository.findAll());
        return "admin/admin_qna";
    }

    @GetMapping("/admin/admin_qnaDetail")
    public String qnaDetail(@RequestParam("id") Long id, Model model) {
        QnaList qnAList = qnaService.getInquiryDetail(id);
        Member member = memberRepository.findById(qnAList.getUid()).get();
        model.addAttribute("member", member);
        model.addAttribute("qna", qnAList);
        model.addAttribute("lists", qnaService.getInquiryReplies(id));
        model.addAttribute("now", getCurrentTime());
        return "admin/admin_qnaDetail";
    }

    @PostMapping("/admin/inquiryInsert")
    public String qnaInsert(@ModelAttribute QnaList list,
                            @RequestParam String content,
                            HttpSession session) {
        qnaService.createInquiry(list, content, session);
        return "redirect:/admin/admin_qnaDetail";
    }

    @PostMapping("/admin/answerInsert")
    public String answerInsert(@RequestParam String content,
                               @RequestParam String role,
                               @RequestParam Long id) {
        qnaService.addAnswer(content, role, id);
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

    // -------------------------------------------
    // FAQ 관리
    // -------------------------------------------

    @GetMapping("/admin/admin_faq")
    public String faq(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("faqs", faqService.getAllFAQs(pageable));
        return "admin/admin_faq";
    }

    @GetMapping("/admin/admin_faqDetail")
    public String faqDetail(@RequestParam Long id, Model model) {
        model.addAttribute("now", getCurrentTime());
        model.addAttribute("faq", faqService.faqDetail(id));
        return "admin/admin_faqDetail";
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
        return "redirect:/admin/admin_faq";
    }

    @PostMapping("/admin/admin_faqUpdate")
    public String updateFAQ(@RequestParam Long id,
                            @RequestParam String title,
                            @RequestParam String question,
                            @RequestParam String answer) {
        faqService.faqUpdate(id, title, question, answer);
        return "redirect:/admin/admin_faq";
    }

    @PostMapping("/admin/admin_faqDelete")
    public String faqDelete(@RequestParam Long id, Model model) {
        faqService.faqDelete(id);
        MessageDto message = new MessageDto("삭제하였습니다", "/admin_faq", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // -------------------------------------------
    // 입지분석 신청 관리
    // -------------------------------------------

    // 신청내역 목록/신청내역 상세/신청한 입지분석 적용 후 완료된 내역을 메일로?? 어떻게 해야하지
    @GetMapping("/admin/admin_apply")
    public String apply(Model model) {
        model.addAttribute("applies", applyRepository.findAll());
        model.addAttribute("now", getCurrentTime());
        return "admin/admin_apply";
    }

    @GetMapping("/admin/admin_apply/detail")
    public String applyDetail(@RequestParam Long id, Model model) {
        model.addAttribute("apply", applyService.selectApplyDetail(id));
        model.addAttribute("now", getCurrentTime());
        return "admin/admin_applyDetail";
    }

    @PostMapping("/admin/admin_apply_result")
    public String applyResult(Apply apply){
        Apply updateApply = applyRepository.selectApplyDetail(apply.getId());
        updateApply.setCompletedYn('Y');
        updateApply.setLink(apply.getLink());
        updateApply.setLocation(apply.getLocation());
        updateApply.setType(apply.getType());
        applyService.updateApply(updateApply);
        return "redirect:/admin/admin_apply";
    }
}
