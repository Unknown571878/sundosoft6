package edu.du.project2.controller;

import edu.du.project2.dto.MessageDto;
import edu.du.project2.entity.Member;
import edu.du.project2.entity.QnaList;
import edu.du.project2.repository.MemberRepository;
import edu.du.project2.repository.QnaListRepository;
import edu.du.project2.dto.AuthInfo;
import edu.du.project2.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Q&A 관련 요청을 처리하는 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
@Slf4j
public class QnaController {

    private final QnaService qnaService;
    private final QnaListRepository qnAListRepository;
    private final MemberRepository memberRepository;

    /**
     * 메시지와 리다이렉트를 처리하는 공통 메서드.
     *
     * @param params 메시지와 리다이렉트 정보를 포함한 DTO
     * @param model  뷰로 데이터를 전달하기 위한 객체
     * @return 메시지 출력 및 리다이렉트 페이지 경로
     */
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    // Q&A 목록 페이지를 반환합니다.
    @GetMapping("/inquiry")
    public String qnaList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable, HttpSession session) {
        AuthInfo authInfo = getAuthInfo(session, model);
        if (authInfo == null) return showMessageAndRedirect(new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null), model);

        model.addAttribute("now", LocalDateTime.now());
        Page<QnaList> inquiries = qnaService.getInquiries(authInfo, pageable);
        model.addAttribute("inquiries", inquiries);
        model.addAttribute("posts", qnaService.getInquiryList(authInfo));
        return "/qna/inquiry";
    }


    // Q&A 상세 페이지를 반환합니다.
    @GetMapping("/inquiryDetail")
    public String qnaDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("qna", qnaService.getInquiryDetail(id));
        model.addAttribute("lists", qnaService.getInquiryReplies(id));
        QnaList qnAList = qnaService.getInquiryDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        Member member = memberRepository.findById(qnAList.getUid()).get();
        model.addAttribute("member", member);
        return "/qna/inquiryDetail";
    }

    // Q&A 작성 페이지를 반환합니다.
    @GetMapping("/inquiryInsertForm")
    public String qnaInsertForm() {
        return "/qna/inquiryInsertForm";
    }

    // 새로운 Q&A를 생성합니다.
    @PostMapping("/inquiryInsert")
    public String qnaInsert(@ModelAttribute QnaList list,
                            @RequestParam String content,
                            HttpSession session) {
        qnaService.createInquiry(list, content, session);
        return "redirect:/qna/inquiry";
    }

    // Q&A 답변을 추가합니다.
    @PostMapping("/answerInsert")
    public String answerInsert(@RequestParam String content,
                               @RequestParam String role,
                               @RequestParam Long id) {
        qnaService.addAnswer(content, role, id);
        return "redirect:/qna/inquiryDetail?id=" + id;
    }

    // Q&A를 종료합니다.
    @PostMapping("end/{id}")
    public String end(@PathVariable Long id, Model model) {
        QnaList qnaList = qnAListRepository.findById(id).get();
        qnaList.setEndYn('Y');
        qnAListRepository.save(qnaList);
        MessageDto message = new MessageDto("문의를 종료하였습니다.", "/qna/inquiry", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 공통 메서드: 세션에서 AuthInfo 가져오기
    private AuthInfo getAuthInfo(HttpSession session, Model model) {
        return (AuthInfo) session.getAttribute("authInfo");
    }
}
