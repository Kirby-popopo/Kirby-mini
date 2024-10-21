package com.example.Mini1st.controller;

import com.example.Mini1st.dao.search.SearchDTO;
import com.example.Mini1st.dao.search.SearchMapper;
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

}
