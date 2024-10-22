package com.example.Mini1st.dao.follow;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FollowMapper {
    @Update("delete from follows where follower_id = #{follow} and following_id = #{target}")
    public boolean unfollow(String follow, String target);

    @Update("insert into follows (follower_id, following_id) value (#{follower_id}, #{following_id})")
    public boolean follow(FollowDTO follow);
}
