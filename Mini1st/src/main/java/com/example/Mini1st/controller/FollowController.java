package com.example.Mini1st.controller;

import com.example.Mini1st.dao.follow.FollowDTO;
import com.example.Mini1st.dao.follow.FollowMapper;
import com.example.Mini1st.dao.login.UserDTO;
import com.example.Mini1st.dao.profile.ProfileMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class FollowController {
    @Autowired
    FollowMapper followMapper;

    @Autowired
    ProfileMapper profileMapper;

    @PostMapping("/UnFollow")
    public int UnFollow(@RequestBody Map<String, String> requestData, Model model, HttpSession session) {
        /***********************************************************
         테스트 영역
         **********************************************************/
        String targetUser = requestData.get("targetUser");
        UserDTO user = (UserDTO) session.getAttribute("loginMember");
        String userId = user.getUser_id();

        try {
            followMapper.unfollow(userId, targetUser);

            List<FollowDTO> findFollow = profileMapper.findFollowUser(targetUser);
            model.addAttribute("followingCount", findFollow.size());
            return findFollow.size();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("unFollow" + userId);
        return 0;
    }

    @PostMapping("/Follow")
    public int Follow(@RequestBody Map<String, String> requestData, Model model, HttpSession session) {
        /***********************************************************
         테스트 영역
         **********************************************************/
        String targetUser = requestData.get("targetUser");
        UserDTO user = (UserDTO)session.getAttribute("loginMember");

        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollower_id(user.getUser_id());
        followDTO.setFollowing_id(targetUser);

        try {
            followMapper.follow(followDTO);

            // 팔로우 테이블 - 나를 팔로우 하고 있는 유저 가져오기
            List<FollowDTO> findFollow = profileMapper.findFollowUser(targetUser);
            System.out.println(model.getAttribute("followingCount"));

            return findFollow.size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
