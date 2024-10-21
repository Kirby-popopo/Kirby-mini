package com.example.Mini1st.dao;


import lombok.Data;

import java.time.LocalDateTime;

// DTO : (목적)데이터 전송 // 데이터계층 <--> 뷰
// 계층간 정보를 안전하게 주고 받기 위해 사용
// DB 데이터 조회 후 UI전달하거나
// 사용자 입력 데이터를 받아 서비스 계층으로 전달할때
@Data
public class PostDTO {
    private String userId;
    private String contents;
    private String location;
    private int likesCount;
    private String image_link;
}

