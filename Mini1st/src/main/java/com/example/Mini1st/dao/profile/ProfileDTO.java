package com.example.Mini1st.dao.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileDTO {
    private String user_id;
    private String name;
    private String profile_image;
    private String description;
}
