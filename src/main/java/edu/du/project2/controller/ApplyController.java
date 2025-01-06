package edu.du.project2.controller;

import edu.du.project2.entity.Apply;
import edu.du.project2.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // 신청서 목록 페이지를 반환
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Apply> page = getPagedApplies(pageable);
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("applies", page);
        return "map/apply_list";
    }

    // 신청서 상세 페이지를 반환
    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Apply apply = applyService.selectApplyDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("apply", apply);
        model.addAttribute("now", now);
        return "map/apply_detail";
    }

    // 신청서 작성 페이지를 반환
    @GetMapping("")
    public String write(){
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

    // 페이징 처리 로직 분리
    private Page<Apply> getPagedApplies(Pageable pageable) {
        List<Apply> applies = applyService.findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), applies.size());
        return new PageImpl<>(applies.subList(start, end), pageable, applies.size());
    }
}
