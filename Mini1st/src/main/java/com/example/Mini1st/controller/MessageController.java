package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Message")
public class MessageController {
    public ModelAndView MainPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("messagePage");
        return mav;
    }
}
