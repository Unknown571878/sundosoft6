package edu.du.project2.controller;


import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import edu.du.project2.repository.QnARepository;
import edu.du.project2.repository.QnA_ListRepository;
import edu.du.project2.dto.AuthInfo;
import edu.du.project2.service.MessageDto;
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

    private final QnA_ListRepository qnAListRepository;
    private final QnARepository qnARepository;

    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    @GetMapping("/inquiry")
    public String qna(Model model, @PageableDefault(page=0,size=10) Pageable pageable, HttpSession session) {
        // 로그인을 확인하고 로그인이 되어있지 않을 경우 메인으로 보냄
        if (session.getAttribute("authInfo") == null) {
            MessageDto message = new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
        // 현재 사용자의 세션 정보 받아서 불러옴
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo.getRole().equals("ADMIN")) {
            List<QnAList> list = qnAListRepository.findAllByOrderByIdDesc();

            final int start = (int) pageable.getOffset();
            // 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
            final int end = Math.min((start + pageable.getPageSize()), list.size());
            // 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
            final Page<QnAList> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
            // 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
            model.addAttribute("inquirys", page);
            return "/qna/inquiry";
        }
        // 사용자의 ID정보로 QnA 리스트를 불러옴
        List<QnAList> posts = qnAListRepository.findAllByUidOrderByIdDesc(authInfo.getId());
        // 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
        final int start = (int) pageable.getOffset();
        // 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
        final int end = Math.min((start + pageable.getPageSize()), posts.size());
        // 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
        final Page<QnAList> page = new PageImpl<>(posts.subList(start, end), pageable, posts.size());
        // 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
        model.addAttribute("inquirys", page);
        model.addAttribute("posts", posts);
        return "/qna/inquiry";
    }

    @GetMapping("/inquiryDetail")
    public String qnadetail(@RequestParam("id") Long id, Model model) {
        QnAList qna = qnAListRepository.findById(id).orElse(null);
        List<QnA> lists = qnARepository.findAllByQnaIdOrderByIdAsc(qna);
        model.addAttribute("qna", qna);
        model.addAttribute("lists", lists);
        return "/qna/inquiryDetail";
    }

    @GetMapping("/inquiryInsertForm")
    public String qnaInserForm() {
        return "/qna/inquiryInsertForm";
    }

    @PostMapping("/inquiryInsert")
    public String qnaInsert(@ModelAttribute QnAList list,
                            @RequestParam String content, Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        QnAList qnalist = QnAList.builder()
                .title(list.getTitle())
                .created_at(LocalDateTime.now())
                .endYn('N')
                .state('Q')
                .uid(list.getUid())
                .build();
        QnAList saveQnA = qnAListRepository.save(qnalist);

        QnA qna = QnA.builder()
                .qnaId(saveQnA)
                .content(content)
                .state('Q')
                .build();
        qnARepository.save(qna);
        return "redirect:/qna/inquiry";
    }

    @PostMapping("/answerInsert")
    public String answerInsert(@RequestParam String content,
                               @RequestParam String role,
                               @RequestParam Long id){
        QnAList qnalist = qnAListRepository.findById(id).orElse(null);

        QnA qna = QnA.builder()
                .qnaId(qnalist)
                .content(content)
                .state(role.equals("admin") ? 'A' : 'Q')
                .build();
        qnARepository.save(qna);

        QnAList qnAList = qnAListRepository.findById(id).orElse(null);
        qnAList.setState(role.equals("admin") ? 'A' : 'Q');
        qnAListRepository.save(qnAList);

        return "redirect:/qna/inquiryDetail?id=" + id;
    }
}
