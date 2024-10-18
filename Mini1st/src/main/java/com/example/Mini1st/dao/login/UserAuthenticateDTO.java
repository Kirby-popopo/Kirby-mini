package com.example.Mini1st.dao.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAuthenticateDTO {
    private String userId;
    private String userPw;
    private String email;
    private String name;
    private String gender;
    private String nickname;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String profileImage;
}
