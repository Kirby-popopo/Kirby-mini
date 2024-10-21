package com.example.Mini1st.controller;

import com.example.Mini1st.dao.login.UserDTO;
import com.example.Mini1st.dao.profile.ProfileDummyDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class FollowController {
    @PostMapping("/UnFollow")
    public void show(@RequestBody Map<String, String> requestData, Model model) {
        /***********************************************************
         테스트 영역
         **********************************************************/

        System.out.println(requestData.get("targetUser"));

    }
}
