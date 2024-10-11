package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class testController {

    @GetMapping("/")
    public String test() {
        return "mainPage";
    }


}
