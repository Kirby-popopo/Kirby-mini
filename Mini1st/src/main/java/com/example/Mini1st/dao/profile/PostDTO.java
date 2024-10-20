package com.example.Mini1st.dao.profile;

import lombok.Data;

@Data
public class PostDTO {
    private int post_pk;
    private String user_id;
    private String contents;
    private String image_link;
}
