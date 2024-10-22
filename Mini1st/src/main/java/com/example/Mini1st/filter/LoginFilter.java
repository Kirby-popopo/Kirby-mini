package com.example.Mini1st.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;


@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // /signup, login 경로에 대해 필터를 제외하는 조건
        if (requestURI.startsWith("/signup") || requestURI.startsWith("/login")) {
            // 제외된 경로의 경우 필터를 거치지 않고 다음 체인으로 이동
            chain.doFilter(request, response);
            return;
        }

        // 세션에서 로그인 여부 확인 (로그인 안된 경우 로그인 페이지로)
        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("loginMember") == null){
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
