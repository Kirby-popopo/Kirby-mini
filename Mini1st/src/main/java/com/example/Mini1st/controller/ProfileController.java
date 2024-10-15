package com.example.Mini1st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
   /* @GetMapping("/${name}")
    public String showProfilePageUser(@RequestParam String name) {
        *//**********************************************************
         *                  프로필 영역
         *          name get으로 받고 해당 유저의 프로필 정보를 가져옴.
         * 1. 유저 테이블에서 유저의 정보를 가져옴
         * 2. 게시글 테이블에서 게시글을 전부 가져옴.
         * 3. 친구 테이블에서 나를 친구 추가한 사람 추출
         * 4. 그 반대의 경우도 추출
         * (카운트랑 실 데이터랑 두개 만들지 아니면 실 데이터 기반으로 카운트를 셀지 고민)
         * 5. html로 전부 전달.
         **********************************************************//*

        return "profilePage";
    }*/

    @GetMapping("/test")
    public String showProfilePage() {
        /**********************************************************
         *                  테스트용 메인 루트
         **********************************************************/

        return "profilePage";
    }
}
