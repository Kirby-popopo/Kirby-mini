package com.example.Mini1st.dao.search;

import com.example.Mini1st.dao.Gender;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    @Select("select user_id, name, nickname, profile_image from users where user_id like '%${userId}%'")
    public List<SearchDTO> searchUserToUserId(String userId);
}