package edu.du.project2.interceptor;

import edu.du.project2.dto.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private void handleUnauthorizedAccess(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403 상태 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>Unauthorized</title></head>" +
                "<body>" +
                "<script>" +
                "alert('" + message + "');" +
                "window.location='/';" +
                "</script>" +
                "</body>" +
                "</html>";

        response.getWriter().write(html);
        response.getWriter().flush();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        if (session == null || session.getAttribute("authInfo") == null) {
            handleUnauthorizedAccess(response, "로그인이 필요합니다.");
            return false;
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        String userRole = authInfo.getRole();

        if (requestURI.startsWith("/admin") && !"ADMIN".equals(userRole)) {
            handleUnauthorizedAccess(response, "권한이 없습니다.");
            return false;
        }

        return true;
    }
}
