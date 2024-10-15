package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/test")
    public String showProfilePage() {

        return "profilePage";
    }
}
