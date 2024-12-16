package edu.du.project2.controller;


import edu.du.project2.dto.AuthInfo;
import edu.du.project2.dto.MemberRequest;
import edu.du.project2.entity.Member;
import edu.du.project2.repository.MemberRepository;
import edu.du.project2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            model.addAttribute("isLoggedIn", true);  // 로그인된 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 로그인되지 않은 상태
        }
        return "main/home";
    }

    @GetMapping("/register")
    public String register() {
        return "user/loginform";
    }

    @PostMapping("/register")
    public String register(MemberRequest request, Model model) {
        String result = memberService.registerMember(request);
        if ("이미 존재하는 이메일입니다.".equals(result)) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "user/loginform";
        }
        model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login"; // 로그인 폼 페이지
    }

    @PostMapping("/login")
    public String login(MemberRequest request, Model model, HttpSession session) {
        boolean isLogin = memberService.loginMember(request);
        if (!isLogin) {
            model.addAttribute("error", "이메일 또는 비밀번호가 틀립니다.");
            return "user/login";
        }
        Member member = memberRepository.findByEmail(request.getEmail()).orElse(null); // 이메일로 회원 조회
        if (member != null && member.isAdmin()) { // 관리자인지 확인
            AuthInfo authInfo = new AuthInfo(member.getId(), member.getEmail(), member.getName(), member.getRole()); // 관리자 정보를 AuthInfo에 입력
            session.setAttribute("authInfo", authInfo); // 세션에 AuthInfo 저장
            return "redirect:/admin"; // 관리자는 관리자 페이지로 리디렉션
        }
        AuthInfo authInfo = new AuthInfo(member.getId(), member.getEmail(), member.getName(), member.getRole()); // 회원 정보를 AuthInfo에 입력
        session.setAttribute("authInfo", authInfo); // 세션에 AuthInfo 저장
        return "redirect:/"; // 일반 사용자는 홈 페이지로 리디렉션
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화 (로그아웃 처리)
        return "redirect:/login";  // 로그인 페이지로 리디렉션
    }

    @GetMapping("/idfor")
    public String idforPage() {
        return "user/idfor";  // idfor.html로 이동
    }

    @GetMapping("/passwordfor")
    public String passwordforPage() {
        return "user/passwordfor";  // passwordfor.html로 이동
    }

}
