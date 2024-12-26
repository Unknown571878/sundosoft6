package edu.du.project2.controller;

import edu.du.project2.entity.FaQ;
import edu.du.project2.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FAQController {

    private final FAQService faqService;

    @GetMapping("")
    public String faq(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<FaQ> lists = faqService.getUserFAQs(pageable);
        model.addAttribute("faqs", lists);
        return "/faq/faqList";
    }

    @GetMapping("/faqDetail")
    public String faqDetail(@RequestParam("id") Long id, Model model) {
        FaQ faQ = faqService.faqDetail(id);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("faq", faQ);
        model.addAttribute("now", now);
        return "/faq/faqDetail";
    }

}
