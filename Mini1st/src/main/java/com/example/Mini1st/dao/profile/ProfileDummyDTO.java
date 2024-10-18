package com.example.Mini1st.dao.profile;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class ProfileDummyDTO {
    /*
    *   게시글 관련 데이터 더미
    * */
    private int post_pk = 1;
    private String user_id = "1234";
    private String contents = "컨텐츠";
    private Timestamp post_time = Timestamp.valueOf(LocalDateTime.now());
    private String location = "대한민국";
    private int likes_count = 1;
    
    /*
    *   유저 관련 데이터 더미
    * */
    private String name = "문태성 더미";
    private String profile_image = "this is path";
    private String profile_contents = "더미용 컨텐츠입니다. <br> 이건 프로필 설명하는 란에 들어갈 내용입니다.";
    private int follow_count = 1;
    private int following_count = 1;

    public int GetPostCount(){
        return 1;
    }
}
