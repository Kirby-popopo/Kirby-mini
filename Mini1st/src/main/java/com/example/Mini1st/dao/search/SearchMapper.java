package com.example.Mini1st.dao.search;

import com.example.Mini1st.dao.Gender;
import com.example.Mini1st.dao.follow.FollowDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    @Select("select user_id, name, nickname, profile_image from users where user_id like '${userId}%'")
    public List<SearchDTO> searchUserToUserId(String userId);

    @Select("select follower_id from follows where following_id = ${targetId} and follower_id like '${searchId}%'")
    public List<FollowDTO> searchFollowerToUserId(String searchId, String targetId);

    @Select("select following_id from follows where follower_id = ${targetId} and following_id like '${searchId}%'")
    public List<FollowDTO> searchFollowingToUserId(String searchId, String targetId);
}