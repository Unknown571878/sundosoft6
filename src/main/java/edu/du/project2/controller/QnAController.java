package edu.du.project2.controller;

import edu.du.project2.dto.MessageDto;
import edu.du.project2.entity.Member;
import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import edu.du.project2.repository.MemberRepository;
import edu.du.project2.repository.QnARepository;
import edu.du.project2.repository.QnA_ListRepository;
import edu.du.project2.dto.AuthInfo;
import edu.du.project2.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
@Slf4j
public class QnAController {

    private final QnAService qnAService;
    private final QnA_ListRepository qnAListRepository;
    private final MemberRepository memberRepository;

    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    @GetMapping("/inquiry")
    public String qna(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable, HttpSession session) {
        if (session.getAttribute("authInfo") == null) {
            MessageDto message = new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Page<QnAList> inquiries = qnAService.getInquiries(authInfo, pageable);
        model.addAttribute("inquiries", inquiries);
        List<QnAList> lists = qnAService.getInquiryNum(authInfo);
        model.addAttribute("posts", lists);
        return "/qna/inquiry";
    }

    @GetMapping("/inquiryDetail")
    public String qnaDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("qna", qnAService.getInquiryDetail(id));
        model.addAttribute("lists", qnAService.getInquiryReplies(id));
        QnAList qnAList = qnAService.getInquiryDetail(id);
        Member member = memberRepository.findById(qnAList.getUid()).get();
        model.addAttribute("member", member);
        return "/qna/inquiryDetail";
    }

    @GetMapping("/inquiryInsertForm")
    public String qnaInsertForm() {
        return "/qna/inquiryInsertForm";
    }

    @PostMapping("/inquiryInsert")
    public String qnaInsert(@ModelAttribute QnAList list,
                            @RequestParam String content,
                            HttpSession session) {
        qnAService.insertInquiry(list, content, session);
        return "redirect:/qna/inquiry";
    }

    @PostMapping("/answerInsert")
    public String answerInsert(@RequestParam String content,
                               @RequestParam String role,
                               @RequestParam Long id) {
        qnAService.insertAnswer(content, role, id);
        return "redirect:/qna/inquiryDetail?id=" + id;
    }

    @PostMapping("end/{id}")
    public String end(@PathVariable Long id, Model model) {
        QnAList qnAList = qnAListRepository.findById(id).get();
        qnAList.setEndYn('Y');
        qnAListRepository.save(qnAList);
        MessageDto message = new MessageDto("문의를 종료하였습니다.", "/qna/inquiry", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
}
