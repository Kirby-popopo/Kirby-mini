package com.example.Mini1st.controller;

import com.example.Mini1st.dao.login.UserDTO;
import com.example.Mini1st.dao.profile.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    ProfileMapper profileMapper;

    @GetMapping(value = "profile/{id}")
    public String showProfilePageUser(@PathVariable String id, Model model, HttpSession session) {
         try {
             // 유저 테이블 - 유저 정보 가져오기
             UserDTO findUser = profileMapper.findUser(id);
             
             // 게시글 테이블 - 유저가 작성한 게시글 가져오기
             List<PostDTO> findUserPost = profileMapper.findUserPost(id);
             
             // 팔로우 테이블 - 나를 팔로우 하고 있는 유저 가져오기
             List<FollowDTO> findFollow = profileMapper.findFollowUser(id);

             // 팔로우 테이블 - 내가 팔로우 하고 있는 유저 가져오기
             List<FollowingDTO> findFollowing = profileMapper.findFollowingUser(id);

             UserDTO loginUser = (UserDTO)session.getAttribute("loginMember");
             model.addAttribute("isFollow", false);
             if (!loginUser.getUser_id().equals(id)){
                 for (FollowDTO followingUser : findFollow) {
                     if(followingUser.getFollower_id().equals(loginUser.getUser_id())){
                         model.addAttribute("isFollow", true);
                     }
                 }
             }

             model.addAttribute("profile", findUser);
             model.addAttribute("posts", findUserPost);
             model.addAttribute("postsCount", findUserPost.size());
             model.addAttribute("follow", findFollow);
             model.addAttribute("followCount", findFollow.size());
             model.addAttribute("following", findFollowing);
             model.addAttribute("followingCount", findFollowing.size());

         } catch (Exception e) {
             System.out.println("showProfilePageUser Mapper error : " + e.getMessage());
         }

        return "profilePage";
    }

    @GetMapping("/profile")
    public String show(Model model, HttpSession session) {
        /***********************************************************
                테스트 영역
         **********************************************************/

        UserDTO loginUser = (UserDTO)session.getAttribute("loginMember");

        model.addAttribute("loginMember", loginUser);
        //model.addAttribute("post_count", test.GetPostCount());


        return "redirect:profile/" + loginUser.getUser_id();
    }

    @GetMapping("/profileEdit")
    public String showProfileEdit(Model model, HttpSession session) {
        /***********************************************************
         테스트 영역
         **********************************************************/

        UserDTO loginUser = (UserDTO)session.getAttribute("loginMember");

        model.addAttribute("loginMember", loginUser);
        //model.addAttribute("post_count", test.GetPostCount());


        return "profileEditPage";
    }

}
