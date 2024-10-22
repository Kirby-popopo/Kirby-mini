package com.example.Mini1st.controller;

import com.example.Mini1st.dao.follow.FollowDTO;
import com.example.Mini1st.dao.login.UserDTO;
import com.example.Mini1st.dao.search.SearchDTO;
import com.example.Mini1st.dao.search.SearchMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {
    @Autowired
    SearchMapper dao;

    @ResponseBody
    @PostMapping("/SearchUser")
    public List<SearchDTO> SearchUser(@RequestBody Map<String, String> requestData){
        String userId = requestData.get("userId");
        List<SearchDTO> users = new ArrayList<>();
        try {
            if(userId.equals("")){
                return null;
            }
            users = dao.searchUserToUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

    @ResponseBody
    @PostMapping("/profile/searchFollowerUser")
    public List<FollowDTO> SearchFollower(@RequestBody Map<String, String> requestData, HttpSession session){
        String targetId = requestData.get("targetId");
        String userId = requestData.get("userId");

        List<FollowDTO> users = new ArrayList<>();
        try {
            if(targetId.equals("")){
                return null;
            }
            users = dao.searchFollowerToUserId(userId, targetId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

    @ResponseBody
    @PostMapping("/profile/searchFollowingUser")
    public List<FollowDTO> SearchFollowing(@RequestBody Map<String, String> requestData, HttpSession session){
        String targetId = requestData.get("targetId");
        String userId = requestData.get("userId");

        List<FollowDTO> users = new ArrayList<>();
        try {
            if(targetId.equals("")){
                return null;
            }
            users = dao.searchFollowingToUserId(userId, targetId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

}
