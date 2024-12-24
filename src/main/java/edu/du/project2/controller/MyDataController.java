package edu.du.project2.controller;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.dto.MessageDto;
import edu.du.project2.service.MemberService;
import edu.du.project2.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MyDataController {
    private final ProfileService profileService;
    private final MemberService memberService;

    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (session.getAttribute("authInfo") == null) {
            MessageDto message = new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }

        model.addAttribute("my", authInfo);
        return "user/profile"; // 프로필 페이지 반환
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute AuthInfo authInfo, HttpSession session, RedirectAttributes redirectAttributes) {
        profileService.updateProfile(authInfo);
        session.setAttribute("authInfo", authInfo);
        authInfo.setRole("USER");
        redirectAttributes.addFlashAttribute("message", "프로필 정보가 변경되었습니다.");
        return "redirect:/profile";
    }

    @GetMapping("/checkLoginId")
    @ResponseBody
    public String checkLoginId(String loginId) {
        boolean isAvailable = memberService.registerCheckId(loginId);
        if (isAvailable) {
            return "사용 가능한 아이디입니다.";
        } else {
            return "이미 사용 중인 아이디입니다.";
        }
    }

}
