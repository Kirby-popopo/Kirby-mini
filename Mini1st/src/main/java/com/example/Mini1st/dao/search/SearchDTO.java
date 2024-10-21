package com.example.Mini1st.dao.search;

import com.example.Mini1st.dao.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {
    private String user_id;
    private String email;
    private String name;
    private Gender gender;
    private String nickname;
    private String phone_number;
    private String profile_image;
}
