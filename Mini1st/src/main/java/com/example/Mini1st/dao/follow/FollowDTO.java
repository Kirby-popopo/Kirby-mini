package com.example.Mini1st.dao.follow;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {
    private String follower_id;
    private String following_id;
    private LocalDateTime follow_time;
}
