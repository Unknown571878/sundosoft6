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

@Controller
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class ApplyController {
    private final ApplyService applyService;

    @GetMapping("")
    public String analysisList(Model model,
                                @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Apply> applies = applyService.findAll();
        LocalDateTime now = LocalDateTime.now();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), applies.size());
        final Page<Apply> page = new PageImpl<>(applies.subList(start, end), pageable, applies.size());
        model.addAttribute("now", now);
        model.addAttribute("applies", page);
        return "map/apply_list";
    }

    @GetMapping("/detail")
    public String analysisDetail(@RequestParam Long id, Model model) {
        Apply apply = applyService.selectApplyDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("apply", apply);
        model.addAttribute("filePath", apply.getFilePaths());
        model.addAttribute("now", now);
        return "map/apply_detail";
    }

    @GetMapping("/write")
    public String analysisWrite(){
        return "map/apply_write";
    }

    @PostMapping("/analysisApply")
    public String analysisApply(@RequestParam String title,
                                @RequestParam String content,
                                @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0 || files[0].isEmpty()) {
            // 파일이 없는 경우
            applyService.createBoard(title, content);
        } else {
            // 파일이 있는 경우
            applyService.createBoard(title, content, files);
        }
        return "redirect:/analysis";
    }

    @PostMapping("/analysisUpdate")
    public String analysisUpdate(Apply apply){
        applyService.updateApply(apply);
        return "redirect:/analysis";
    }

    @PostMapping("/analysisDelete")
    public String analysisDelete(@RequestParam Long id){
        applyService.deleteApply(id);
        return "redirect:/analysis";
    }
}
