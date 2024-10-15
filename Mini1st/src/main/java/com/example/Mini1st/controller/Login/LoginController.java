package com.example.Mini1st.controller.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mybatis.dao.login.UserMapper;
import com.example.Mini1st.config.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserMapper userMapper;

    @Autowired
    public LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 로그인 페이지 요청
    @GetMapping
    public String loginPage() {
        return "loginPage"; // 로그인 페이지 뷰 이름 반환
    }

    // 로그인 요청 처리
    @PostMapping("/authenticate")
    public String authenticate(UserDTO user, HttpServletRequest request, Model model) {
        String userId = user.getUserid();
        String userPw = request.getParameter("userPw");
        UserDTO foundUser = userMapper.getUserByIdAndPw(userId, userPw);
        if (foundUser != null) {
            HttpSession session = request.getSession(); // 세션 생성
            session.setAttribute("loginMember", foundUser); // 로그인 멤버 정보를 세션에 저장
            return "redirect:/mainPage"; // 로그인 성공 시 홈 화면으로 리다이렉트
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "loginPage"; // 로그인 실패 시 로그인 페이지로 리턴 (에러 메시지 포함)
        }
    }

    // 로그아웃 요청 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    // 홈 화면 요청
    // 수정 : 임시 매핑명 지정
    @PostMapping("/test")
    public String main(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/templates/login"; // 세션이 없거나 로그인 멤버가 없으면 로그인 페이지로 리다이렉트
        }
        UserDTO loginMember = (UserDTO) session.getAttribute("loginMember");
        model.addAttribute("user", loginMember);
        return "mainPage"; // mainPage.html 뷰 반환
    }

}