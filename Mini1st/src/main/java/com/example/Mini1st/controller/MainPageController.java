package com.example.Mini1st.controller;

import com.example.Mini1st.dao.login.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {
    @GetMapping("/")
    public ModelAndView MainPage(Model model, HttpSession session){
        ModelAndView mav = new ModelAndView();
        UserDTO userdto = (UserDTO) session.getAttribute("loginMember");
        String name  = userdto.getName();
        mav.addObject("userName", name);
        mav.setViewName("mainPage");
        return mav;
    }
}
