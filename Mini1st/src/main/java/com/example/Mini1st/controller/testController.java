package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class testController {

    @GetMapping("/")
    public String test() {
        return "message/mainPage";
    }

    @GetMapping("/chat")
    public String showChatPage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "message/messageChat";  // resources/templates/message/messageChat.html
    }

}
