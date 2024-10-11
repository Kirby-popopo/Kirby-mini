package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainPageController {
    public ModelAndView MainPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("mainPage");
        return mav;
    }
}
