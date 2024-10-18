package com.example.Mini1st.controller.SignUp;

import com.example.Mini1st.dao.login.UserAuthenticateDTO;
import mybatis.dao.login.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/signup")
public class SignUpController {

    // 데이터베이스에 접근하기 위한 UserMapper 객체
    private final UserMapper userMapper;

    // 생성자를 통한 의존성 주입 (UserMapper 객체 주입)
    @Autowired
    public SignUpController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 회원가입 페이지 요청 처리
    @GetMapping
    public String signupPage() {
        return "signupPage"; // 회원가입 페이지 뷰 이름 반환
    }

    // 회원가입 요청 처리
    @PostMapping("/join")
    public String join(UserAuthenticateDTO user, Model model) {

        try {
            // 회원 정보를 데이터베이스에 저장하는 로직
            boolean joinUser = userMapper.insert(user);
            if (joinUser) {
                return "redirect:/"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
            } else {
                model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해 주세요.");
                return "signupPage"; // 실패 시 회원가입 페이지로 리턴
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

