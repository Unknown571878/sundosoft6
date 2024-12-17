package edu.du.project2.controller;

import edu.du.project2.dto.MessageDto;
import edu.du.project2.dto.ProfileDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class MyDataController {
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (session.getAttribute("my") == null) {
            MessageDto message = new MessageDto("로그인이 필요한 서비스입니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
        ProfileDto my = (ProfileDto) session.getAttribute("my");


        // 모델에 'my' 객체 추가
        model.addAttribute("my", my);
        return "user/profile"; // 프로필 페이지 반환
    }
}
