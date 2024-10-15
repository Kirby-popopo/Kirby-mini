package com.example.Mini1st.dao.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {
    private String userId;
    private String email;
    private String name;
    private String gender;
    private String nickname;
    private String phone_number;
    private String profile_image;
}
