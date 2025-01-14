package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.dto.MessageDto;
import edu.du.project2.entity.Apply;
import edu.du.project2.service.ApplyService;
import edu.du.project2.utils.PagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 분석 신청서 관련 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class ApplyController {
    private final ApplyService applyService;

    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    // 신청서 목록 페이지를 반환
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Apply> applies = applyService.findAll();
        Page<Apply> page = PagingUtils.createPage(applies, pageable);
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("applies", page);
        return "map/apply_list";
    }

    // 신청서 상세 페이지를 반환
    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Apply apply = applyService.selectApplyDetail(id);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(apply);
        model.addAttribute("apply", apply);
        model.addAttribute("now", now);
        return "map/apply_detail";
    }

    // 신청서 작성 페이지를 반환
    @GetMapping("")
    public String write(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (session.getAttribute("authInfo") == null) {
            MessageDto message = new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
        return "map/apply_write";
    }

    /**
     * 신청서를 저장.
     *
     * @param author  작성자
     * @param title   제목
     * @param content 내용
     * @param files   첨부 파일 배열
     * @return 신청서 목록 페이지로 리다이렉트
     * @throws IOException 파일 저장 실패 시 발생
     */
    @PostMapping("/analysisApply")
    public String save(@RequestParam String author,
                                @RequestParam String title,
                                @RequestParam String content,
                                @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0 || files[0].isEmpty()) {
            applyService.createBoard(author, title, content);
        } else {
            applyService.createBoard(author, title, content, files);
        }
        return "redirect:/analysis/list";
    }

    // 신청서를 수정
    @PostMapping("/analysisUpdate")
    public String update(Apply apply){
        applyService.updateApply(apply);
        return "redirect:/analysis";
    }

    // 신청서를 삭제
    @PostMapping("/analysisDelete")
    public String delete(@RequestParam Long id){
        applyService.deleteApply(id);
        return "redirect:/analysis";
    }

    // 신청 결과 확인
    @PostMapping("/analysisResult")
    public String result(Apply apply, Model model) throws IOException {
        System.out.println(apply);
        String filePath = apply.getLink();
        System.out.println(filePath);
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println(content);
        model.addAttribute("apply", apply);
        model.addAttribute("content", content);
        return "/map/map_result";
    }
}
